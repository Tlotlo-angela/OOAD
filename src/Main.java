public class Main {

    public static void main(String[] args) {

        Customer customer = new Customer("Tlotlo", "Radithamako", "Gaborone");

        Account savings = new SavingsAccount("BZE001", 900, "Main Branch");

        Account investment = new InvestmentAccount("VMA001", 1500, "Main Branch");

        Account cheque = new ChequeAccount("QWE001", 4000, "Main Branch", "Tlotlo Radithamako", "Air Botswana Ltd");

        customer.addAccount(savings);

        customer.addAccount(investment);

        customer.addAccount(cheque);

        investment.deposit(500);

        ((InterestBearing) investment).applyInterest();

        System.out.println("Investment Balance: " + investment.getBalance());

    }

}

