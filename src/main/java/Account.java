public class Account {
    private String accountNumber;
    private String accountType;
    protected double balance;

    public Account(String accountNumber, String accountType, double initialDeposit) { // Methods: deposit(), withdraw(), toString()
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = initialDeposit;
    }

    public void deposit(double amount) {
        balance += amount; // balance = balance + amount
    }

    public void withdraw(double amount) {
        balance -= amount; // balance = balance - amount
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void displayInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: $" + balance);
    }
}