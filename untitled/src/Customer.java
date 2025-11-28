public class Customer {
    private String name;
    private String surname;
    private String address;
    private String accountNumber;
    private String accountType;
    private double balance;
    private double interestRate;

    public Customer(String name, String surname, String address, String accountNumber, String accountType, double balance, double interestRate) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    // -------------------------
    // Getters
    // -------------------------
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public double getInterestRate() { return interestRate; }

    // -------------------------
    // Setters for mutable fields
    // -------------------------
    public void setBalance(double balance) { this.balance = balance; }
    public void setAddress(String address) { this.address = address; }
}
