// Класс, в котором мы заводим объект типа Аккаунт
public class Account {
    public String fullName;
    public String birthDate;
    public String email;
    public String password;
    public boolean blocked;

    Account(String fullName, String birthDate, String email, String password) { // при создании аккаунта не указывается blocked. Он выставляется сам по умполчанию false
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.blocked = false;
    }

    @Override
    public String toString() {
        return "Это аккаунт " + fullName; //Так выводится аккаунт при вызове login
    }
}

