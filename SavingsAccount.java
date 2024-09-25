public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, double initialDeposit, double interestRate) {
        // Call the constructor of the parent class (Account)
        super(accountNumber, "Savings", initialDeposit);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        balance += balance * interestRate / 100;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Interest Rate: " + interestRate + "%");
    }
}
