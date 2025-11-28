import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:bak.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Database created successfully!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
