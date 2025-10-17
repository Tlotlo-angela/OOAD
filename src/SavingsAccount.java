public class SavingsAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.0005; // 0.05%

    public SavingsAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, balance, branch);
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Withdrawals not allowed from Savings Account.");
    }

    @Override
    public void applyInterest() {
        balance += balance * INTEREST_RATE;
    }
}
