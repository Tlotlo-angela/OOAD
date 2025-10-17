public class ChequeAccount extends Account {
    private String customer;
    private String companyAddress;

    public ChequeAccount(String accountNumber, double balance, String branch,
                         String customer, String companyAddress) {
        super(accountNumber, balance, branch);
        this.customer = customer;
        this.companyAddress = companyAddress;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }
}
