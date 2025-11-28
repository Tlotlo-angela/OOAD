import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionsController {

    @FXML
    private Label ourTransactionsLabel;

    @FXML
    private TableView<Transaction> transactionsTable;

    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    @FXML
    private TableColumn<Transaction, Double> amountColumn;

    @FXML
    private Button goButton;

    // Customer/account info
    private String name;
    private String accountNumber;

    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    /**
     * Sets the customer/account info and pre-fills the label.
     * Loads transactions from the database immediately.
     */
    public void setAccountInfo(String name, String accountNumber) {
        this.name = name;
        this.accountNumber = accountNumber;
        ourTransactionsLabel.setText("Transactions for " + name);

        loadTransactions();
    }

    // -------------------------
    // Load transactions from DB
    // -------------------------
    private void loadTransactions() {
        transactionList.clear();

        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY date DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");

                transactionList.add(new Transaction(date, type, amount));
            }

            // Set up table columns
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

            // Fill the table
            transactionsTable.setItems(transactionList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Back button to close this popup.
     */
    @FXML
    private void handleGo() {
        Stage stage = (Stage) goButton.getScene().getWindow();
        stage.close();
    }
}
