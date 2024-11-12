import java.util.*;

public class BankService {
    private Map<String, User> userCredentialHashMap; // Map that stores user credentials and accounts, passed from BankingApp
    private Random random = new Random(); // Random object to generate random account numbers for users

    BankingApp userMenu = new BankingApp(); // BankingApp object to show user menu after each method operation in BankService

    public BankService(Map<String, User> userCredentialHashMap) {
        this.userCredentialHashMap = userCredentialHashMap;
    }

    private String generateAccountNumber() {
        return String.format("%010d", random.nextInt(1000000000));
    }

    public void createAccount(Scanner scanner) {
        String loggedInUser = BankingApp.loggedInUser;
    
        if (loggedInUser == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }
    
        User user = userCredentialHashMap.get(loggedInUser);
        if (user == null) {
            System.out.println("Error: User not found.");
            return;
        }
    
        String accountNumber = generateAccountNumber();
        System.out.print("Enter account type (Checking/Savings): ");
        String accountType = scanner.nextLine();
    
        System.out.print("Enter initial deposit amount: $");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
    
        Account newAccount;
        if ("Checking".equalsIgnoreCase(accountType)) {
            System.out.print("Enter overdraft limit: $");
            double overdraftLimit = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            newAccount = new CheckingAccount(accountNumber, accountType, initialDeposit, overdraftLimit);
        } else {
            newAccount = new Account(accountNumber, accountType, initialDeposit);  // Ensure initialDeposit is passed
        }
    
        user.addAccount(newAccount);
        System.out.println("Account created successfully! Your account number is: " + accountNumber);
    
        BankingApp.showUserMenu();
    }

    public void viewAccount(Scanner scanner) {
        String loggedInUser = BankingApp.loggedInUser;

        if (loggedInUser == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }

        User user = userCredentialHashMap.get(loggedInUser);

        if (user == null) {
            System.out.println("Error: User not found.");
            return;
        }

        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();

        Account account = user.getAccount(accountNumber);

        if (account != null) {
            account.displayInfo();
        } else {
            System.out.println("Account not found.");
        }

        BankingApp.showUserMenu();
    }
    
    public void deleteAccount(Scanner scanner) {
        /*
        for (Map.Entry<String, User> entry : userCredentialHashMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue())
        */


        // Print size and content of the Map
        System.out.println("Size of map is: "
                           + userCredentialHashMap.size());

        // Printing elements in object of Map
        System.out.println(userCredentialHashMap);
        
        BankingApp.showUserMenu();
    }

    public void deposit(Scanner scanner) {
        // Check if the user is logged in
        String loggedInUser = BankingApp.loggedInUser;
        if (loggedInUser == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }
    
        // Retrieve the logged-in user from the userCredentialHashMap
        User user = userCredentialHashMap.get(loggedInUser);
        if (user == null) {
            System.out.println("Error: User not found.");
            return;
        }
    
        // Ask for the account number
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();
    
        // Retrieve the account linked to this account number
        Account account = user.getAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
    
        // Ask for the deposit amount
        System.out.print("Enter an amount to deposit: $");
        double depositAmt = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
    
        // Ensure the deposit amount is positive
        if (depositAmt <= 0) {
            System.out.println("Error: Deposit amount must be positive.");
            return;
        }
    
        // Call the deposit() method of the Account class
        account.deposit(depositAmt);
    
        // Print confirmation message with the updated balance
        System.out.format("Amount deposited successfully! New balance: $%.2f", account.getBalance());
        System.out.println();

    
        // Show the user menu again
        BankingApp.showUserMenu();
    }

    public void withdraw(Scanner scanner) {
        String loggedInUser = BankingApp.loggedInUser;
    
        if (loggedInUser == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }
    
        User user = userCredentialHashMap.get(loggedInUser);
        if (user == null) {
            System.out.println("Error: User not found.");
            return;
        }
    
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();
    
        Account account = user.getAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
    
        while (true) { // Loop to keep asking for a valid amount
            System.out.print("Enter an amount to withdraw: $");
            double withdrawAmt = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
        
            if (account instanceof CheckingAccount) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                // Check if the withdrawal exceeds the balance + overdraft limit
                if (checkingAccount.getBalance() - withdrawAmt < -checkingAccount.getOverdraftLimit()) {
                    System.out.println("Overdraft limit exceeded.");
                    continue; // Loop back to ask for a valid amount
                }
            } else {
                // For savings or any other account types without overdraft
                if (account.getBalance() < withdrawAmt) {
                    System.out.println("Insufficient balance.");
                    continue; // Loop back to ask for a valid amount
                }
            }
    
            // Proceed with the withdrawal
            account.withdraw(withdrawAmt);
            System.out.format("Amount withdrawn successfully! New balance: $%.2f", account.getBalance());
            System.out.println();
            break; // Exit the loop after a successful withdrawal
        }
    
        BankingApp.showUserMenu();
    }
    

    public void transfer(Scanner scanner) {
        String loggedInUser = BankingApp.loggedInUser;
        
        // Ensure the user is logged in
        if (loggedInUser == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }
    
        // Retrieve the user from the map
        User user = userCredentialHashMap.get(loggedInUser);
        if (user == null) {
            System.out.println("Error: User not found.");
            return;
        }
    
        // Display transfer options
        System.out.println("Which accounts would you like to transfer between?");
        System.out.println("1. Checking to Savings");
        System.out.println("2. Savings to Checking");
    
        int transferChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
    
        String fromAccountNumber, toAccountNumber; // String variables to gather account numbers
        Account fromAccount, toAccount; // Account class variables to gather and match the correct account numbers
    
        // Ask for the amounts and account numbers based on user's choice
        if (transferChoice == 1) {
            System.out.print("Enter your Checking account number: ");
            fromAccountNumber = scanner.nextLine().trim();
    
            System.out.print("Enter your Savings account number: ");
            toAccountNumber = scanner.nextLine().trim();
    
            // Get the accounts from the user
            fromAccount = user.getAccount(fromAccountNumber);
            toAccount = user.getAccount(toAccountNumber);
            
            // Check if both accounts exist and are of the right type
            if (!(fromAccount instanceof CheckingAccount) || toAccount == null || !"Savings".equalsIgnoreCase(toAccount.getAccountType())) {
                System.out.println("Error: Invalid account types or account not found.");
                return;
            }
        } else if (transferChoice == 2) {
            System.out.print("Enter your Savings account number: ");
            fromAccountNumber = scanner.nextLine().trim();
    
            System.out.print("Enter your Checking account number: ");
            toAccountNumber = scanner.nextLine().trim();
    
            // Get the accounts from the user
            fromAccount = user.getAccount(fromAccountNumber);
            toAccount = user.getAccount(toAccountNumber);
    
            // Check if both accounts exist and are of the right type
            if (!"Savings".equalsIgnoreCase(fromAccount.getAccountType()) || !(toAccount instanceof CheckingAccount)) {
                System.out.println("Error: Invalid account types or account not found.");
                return;
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        while(true) {
            // Ask for the transfer amount
            System.out.print("Enter the amount to transfer: $");
            double transferAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline
        
            // Check if the source account has enough funds
            if (fromAccount.getBalance() < transferAmount) {
                System.out.println("Error: Insufficient funds in the source account.");
                continue;
            }
        
            // Deduct money from the source account and transfer to other account
            fromAccount.withdraw(transferAmount);
            toAccount.deposit(transferAmount);
        
            System.out.println("Transfer successful! New balances:");
            System.out.format("Checking account balance: $%.2f\n", user.getAccount(fromAccountNumber).getBalance());
            System.out.format("Savings account balance: $%.2f\n", user.getAccount(toAccountNumber).getBalance());
        
        break;
        }
        
    
        BankingApp.showUserMenu(); // Return to user menu
    }
    

    public void checkBalance(Scanner scanner) {
        // Check if the user is logged in
        String loggedInUser = BankingApp.loggedInUser;
        if (loggedInUser == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }
    
        // Retrieve the logged-in user from the userCredentialHashMap
        User user = userCredentialHashMap.get(loggedInUser);
        if (user == null) {
            System.out.println("Error: User not found.");
            return;
        }
    
        // Ask for the account number
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();
    
        // Retrieve the account linked to this account number
        Account account = user.getAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.format("Your current balance is: $%.2f", account.getBalance());
        System.out.println();

        BankingApp.showUserMenu();
    }
}