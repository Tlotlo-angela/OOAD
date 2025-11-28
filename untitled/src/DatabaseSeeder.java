import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSeeder {

    public static void main(String[] args) {
        Database.initialize();

        // Sample customers
        insertCustomer("Tlotlo", "Radithamako", "Mmopane", "ACC001", "Savings", 5000.0, 0.5, "tlotlo", "tlotlo123");
        insertCustomer("Ewetse", "Ndala", "Gaborone", "ACC002", "Investment", 10000.0, 2.0, "ewetse", "ewetse456");
        insertCustomer("Dikabelo", "Enock", "Francistown", "ACC003", "Cheque", 2000.0, 0.0, "dikabelo", "dk123");
        insertCustomer("Thabo", "Lere", "Molepolole", "ACC004", "Savings", 5000.0, 0.5, "thabo", "thabo789");
        insertCustomer("Bophelo", "Tshephang", "Kanye", "ACC005", "Investment", 5000.0, 0.5, "bophelo", "bp456");
        insertCustomer("Dimpho", "Thabang", "Block 5", "ACC006", "Cheque", 5000.0, 0.5, "dimpho", "dimpho123");
        insertCustomer("Warona", "Andy", "Phase 2", "ACC007", "Savings", 5000.0, 0.5, "warona", "wawa123");
        insertCustomer("Abigail", "West", "Broadhurst", "ACC008", "Investment", 5000.0, 0.5, "abigail", "abigail123");
        insertCustomer("Nara", "Smith", "New York", "ACC009", "Cheque", 5000.0, 0.5, "nara", "naraziza123");
        insertCustomer("Aurora", "Skye", "Dublin", "ACC010", "Savings", 5000.0, 0.5, "aurora", "aurora123");

        System.out.println("Sample customers added (duplicates ignored)!");
    }

    private static void insertCustomer(String name, String surname, String address,
                                       String accountNumber, String accountType,
                                       double balance, double interestRate,
                                       String username, String password) {


        String sql = "INSERT OR IGNORE INTO customers " +
                "(name, surname, address, account_number, account_type, balance, interest_rate, username, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, address);
            pstmt.setString(4, accountNumber);
            pstmt.setString(5, accountType);
            pstmt.setDouble(6, balance);
            pstmt.setDouble(7, interestRate);
            pstmt.setString(8, username);
            pstmt.setString(9, password);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting customer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
