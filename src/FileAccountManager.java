import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class FileAccountManager implements AccountManager {
    File file = new File("c://Intel//testFile2.txt"); // Файл - база данных аккаунтов
    static List<Account> accountList; // Список аккаунтов, который записывается в базу данных
    public FileAccountManager() throws IOException {
        this.accountList = new ArrayList<Account>();
        List<String> content = Files.readAllLines(Paths.get("c://Intel//testFile2.txt")); // Получаем список, в котором каждый элемент это строка из бд
        int counter = 0;
        while (content.size() > counter) { // превращаем этот список строк в Аккаунты и записываем их в список аккаунтов
            List<String> a = List.of(content.get(counter).split(","));
            Account accich = new Account(a.get(0),a.get(1),a.get(2),a.get(3));
            accountList.add(accich);
            counter += 1;
        }

    }
    // Функция, которая блокирует (то есть переводит blocked из false в true) для аккаунтов, которые лежат в списке плохих почт. См. FailedLoginCounter для подробностей
    private void banAccounts(String emmail) throws IOException {
        FailedLoginCounter failedLoginCounter = new FailedLoginCounter(emmail);
        for (Account acc: accountList) {
            if (failedLoginCounter.badEmails.contains(acc.email)){
                System.out.println("Сейчас должно была произойти замена false на true, но она не произошла. Почему-то(");
                acc.blocked = true;
            }
        }
    }

    // Функция, которая зануляет базу данных с аккаунтами
    private void nullBase() throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write("");
        writer.close();
    }
    //Функция для изменения базы данных. Каждый раза база перезаписывается с нуля, основываясь на списке accountList
    private void baseChange() throws IOException {
        nullBase();
        FileWriter writer = new FileWriter(file);
        for (Account acc: accountList) {
            writer.write(acc.fullName+","+acc.birthDate+","+acc.email+","+acc.password+","+acc.blocked+"\n");
        }
        writer.close();
    }

    // Регистрируем нового пользователя. Если хотя бы один аккаунт в бд уже содержит эту почту, то ошибка. Если нет, то добавляется в список аккаунтов
    public void register(Account account) throws AccountAlreadyExistsException, IOException {

        boolean checker = false;
        for (Account acc : accountList) {
            if (acc.email.equals(account.email)) {
                checker = true;
                throw new AccountAlreadyExistsException("Этот аккаунт уже существует (AccountAlreadyExistsException)");
            }
        }
        if (checker == false) {
            accountList.add(account);
        }
        baseChange(); // меняем базу после добавления нового аккаунта
    }

    // Удаляем аккаунт по почте и паролю. Если у какого-то аккаунта из бд совпали пароль и почта, то он удаляется из списка аккаунтов. Если нет, то ошибка
    public void removeAccount(String email, String password) throws WrongCredentialsException, IOException {

        boolean checker = false;
        for (Account acc : accountList) {
            if (acc.email == email && acc.password == password) {
                checker = true;
                accountList.remove(acc);
            }
        }
        if (checker == false) {
            throw new WrongCredentialsException("Неверный email или пароль (WrongCredentialsException)");
        }
        baseChange(); // меняем базу после удаления аккаунта
    }

    // По почтк и паролю получаем аккаунт. Если такой аккаунт находится и он не заблокирован, то мы его отправляем в return. Если он заблокирован, то ошибка. Если неверные данные, то ошибка + запускаем процесс бана аккаунтов, который проверяет надо ли банить аккаунт
    public Account login(String email, String password) throws AccountBlockedException, WrongCredentialsException, IOException {
        boolean checker = false;
        Account nededdAcc = new Account("s","1","d","d");

        for (Account acc : accountList) {
            if (acc.email.equals(email) && acc.password.equals(password) && acc.blocked == false) {
                checker = true;
                nededdAcc = acc;
            } else if (acc.email.equals(email) && acc.password.equals(password) && acc.blocked == true) {
                throw new AccountBlockedException("Аккаунт заблокирован (AccountBlockedException)");
            }
        }
        if (checker == false) {
            banAccounts(email); // добавляем почту в лог и проверяем, что почт меньше 5. Если больше, то бан аккаунта

            throw new WrongCredentialsException("Неверная информация (WrongCredentialsException)");
        }
        return nededdAcc;
    }
}