import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Этот класс (FileService), в котором непосредственно формируется база данных аккаунтов
// Общий принцип работы всех классов в совокупности: у нас есть 2 базы данных. Одна из них содержит аккаунты, другая почты. В базу данных аккаунтов можно попасть только с помощью функции register. В лог же попадают почты, которые использовались при неправильном login
// Если почта находится в базе данных лог больше 4 раз, то такая почта баниться, то есть blocked в true. Почему-то у меня этот момент не работает, хотя программа точно доходит до этого момента. Сейчас просто выводится текст в консоль, когда это должно произойти. См. FileAccountManager -> banAccounts
// Если почта не прошла логин, то она отправляется в banAccounts. Оттуда в FailedLoginCounter, где она добавляется в лог. Там же проверяется и формируется список плохих почт, которые потом используются в banAccounts
// Класс Accounts - класс, в котором формируется тип данных Аккаунт
// AccountManager - интерфейс, из которого наследуются основыные функции и ошибки класса FileAccountManager
// FileAccountManager - класс, в котором выполняется регистрация, удаление и получение информации об аккаунте + создание самой базы данных аккаунтов. Так же там запускается процесс бана и FailedLoginCounter
// FailedLoginCounter - класс, в котором формируется лог, а также список плохих почт, которые нужно забанить
public class FileService {
    public static void main(String[] args) throws IOException {
        FileAccountManager files = new FileAccountManager();
        Account account1 = new Account("name","data","pocht","parol");
        Account account2 = new Account("nam","data","poch","paroll");
        Account account3 = new Account("name2","data2","pocht2","parol2");
        Account account4 = new Account("nam2","data2","poch2","paroll2");
        Account account5 = new Account("name3","data3","pocht3","parol3");
        Account account6 = new Account("nam3","data3","poch3","paroll3");

        try {
           files.register(account1);
           //files.register(account4);
           //files.register(account3);
           System.out.println(files.login("pocht","parolc"));
           //files.removeAccount("pocht","parol");

        } catch (AccountManager.AccountAlreadyExistsException | AccountManager.AccountBlockedException | AccountManager.WrongCredentialsException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
