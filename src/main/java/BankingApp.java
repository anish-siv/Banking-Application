import java.util.*;

public class BankingApp {
    static HashMap<String, User> userCredentialHashMap = new HashMap<>(); // HashMap to store user credentials
    static String loggedInUser = null;
    static Scanner myObj = new Scanner(System.in);

    public static void main(String[] args) {
        showMainMenu(); // Calling the showMainMenu() method
    }

    // Main Menu
    public static void showMainMenu() {
        System.out.println("---------------------");
        System.out.println("Banking Application |");
        System.out.println("1. Sign Up          |");
        System.out.println("2. Login            |");
        System.out.println("3. Exit             |");
        System.out.println("---------------------");

        int choice = myObj.nextInt();
        myObj.nextLine(); // Consuming newline for formatting

        switch (choice) {
            case 1:
                signUp(); // Calling the signUp() method
                break;
            case 2:
                login(); // Calling the login() method
                break;
            case 3:
                exit(); // Calling the exit() method;
                break;
            default:
                System.out.println("Error: invalid choice"); // Exits if a valid choice is not chosen
                System.exit(1);
        }
    }

    // User Menu
    public static void showUserMenu() {
        System.out.println("---------------------");
        System.out.println("1. Create Account   |");
        System.out.println("2. View Account     |");
        System.out.println("3. Delete Account   |");
        System.out.println("4. Deposit          |");
        System.out.println("5. Withdraw         |");
        System.out.println("6. Transfer         |");
        System.out.println("7. Check Balance    |");
        System.out.println("8. Logout           |");
        System.out.println("---------------------");

        int choice = myObj.nextInt();
        myObj.nextLine();  // Consuming newline for formatting

        // BankService object 
        BankService bankService = new BankService(userCredentialHashMap);

        switch (choice) {
            case 1:
                bankService.createAccount(myObj); // Calling the BankService createAccount() method
                break;
            case 2:
                bankService.viewAccount(myObj); // Calling the BankService viewAccount() method
                break;
            case 3:
                bankService.deleteAccount(myObj); // Calling the BankService deleteAccount() method
                break;
            case 4:
                bankService.deposit(myObj); // Calling the BankService deposit() method
                break;
            case 5:
                bankService.withdraw(myObj); // Calling the BankService withdraw() method
                break;
            case 6:
                bankService.transfer(myObj); // Calling the BankService transfer() method
                break;
            case 7:
                bankService.checkBalance(myObj); // Calling the BankService checkBalance() method
                break;
            case 8:
                logout(); // Calling the logout() method
                break;
            default:
                System.out.println("Invalid choice. Try again."); // Loops to user menu if a valid choice is not chosen
                showUserMenu();
                break;
        }
    }

    // Sign-up method
    static void signUp() {
        System.out.print("Enter a username: ");
        String username = myObj.nextLine();

        System.out.print("Enter a password: ");
        String password = myObj.nextLine();

        userCredentialHashMap.put(username, new User(username, password));

        System.out.println("User registered successfully!");
        showMainMenu();
    }

    // Login method
    static void login() {
        System.out.print("Username: ");
        String username = myObj.nextLine();

        System.out.print("Password: ");
        String password = myObj.nextLine();

        User user = userCredentialHashMap.get(username); // Create User object to retrieve username from HashMap

        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = username;
            System.out.println("Login successful!");
            showUserMenu();
        } else {
            System.out.println("Invalid credentials.");
            showMainMenu();
        }
    }

    // Logout method
    static void logout() {
        System.out.println("Goodbye!");
        showMainMenu();
    }

    // Exit method
    static void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}