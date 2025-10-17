import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private String surname;
    private String address;
    private List<Account> accounts;

    public Customer(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
