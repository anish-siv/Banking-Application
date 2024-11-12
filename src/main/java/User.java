import java.util.Map;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private Map<String, Account> accounts; // Store accounts by their number

    // User class parameterized constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accounts = new HashMap<>();
    }

    // Username getter
    public String getUsername() {
        return username;
    }

    // Password getter
    public String getPassword() {
        return password;
    }

    // Accounts getter
    public Map<String, Account> getAccounts() {
        return accounts;
    }

    // Method to add an account
    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    // Account getter
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Password: " + password;
    }
}
