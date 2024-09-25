public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountType, double initialDeposit, double overdraftLimit) {
        // Call the constructor of the parent class (Account)
        super(accountNumber, "Checking", initialDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance + overdraftLimit) {
            balance -= amount;
        } else {
            System.out.println("Overdraft limit exceeded.");
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Overdraft Limit: $" + overdraftLimit);
    }
}
