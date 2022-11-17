import java.io.FileNotFoundException;
import java.io.IOException;

// Интерфейс, из которого мы наследуем ошибки и основные функции (указаны в задании) для класса FileAccountManager
public interface AccountManager {
    void register (Account account) throws AccountAlreadyExistsException, IOException;
    Account login(String email, String password) throws WrongCredentialsException, AccountBlockedException, IOException;
    void removeAccount(String email, String password) throws WrongCredentialsException, IOException;

    public class AccountAlreadyExistsException extends Exception {
        public AccountAlreadyExistsException(String message) {
            super(message);
        }
    }

    public class WrongCredentialsException extends Exception {
        public WrongCredentialsException(String message) {
            super(message);
        }
    }

    public class AccountBlockedException extends Exception {
        public AccountBlockedException(String message) {
            super(message);
        }
    }




}
