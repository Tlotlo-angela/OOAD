import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:bak.db";


    public static Connection getConnection() throws SQLException {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // prints error if driver is not found
        }


        return DriverManager.getConnection(DB_URL);
    }


    public static void initialize() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {


            stmt.execute("PRAGMA foreign_keys = ON;");


            stmt.execute("""
                CREATE TABLE IF NOT EXISTS customers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    surname TEXT NOT NULL,
                    address TEXT NOT NULL,
                    account_number TEXT UNIQUE NOT NULL,
                    account_type TEXT NOT NULL,
                    balance REAL DEFAULT 0,
                    interest_rate REAL DEFAULT 0,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL
                );
            """);


            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    account_number TEXT NOT NULL,
                    type TEXT NOT NULL,
                    amount REAL NOT NULL,
                    date TEXT NOT NULL,
                    FOREIGN KEY (account_number) REFERENCES customers(account_number)
                );
            """);

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertTransaction(String accountNumber, String type, double amount, String date) {
        String sql = "INSERT INTO transactions (account_number, type, amount, date) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, date);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting transaction: " + e.getMessage());
        }
    }


    public static void updateBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE customers SET balance = ? WHERE account_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }
}
