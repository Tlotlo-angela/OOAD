import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DepositController {

    @FXML
    private Label depositLabel;

    @FXML
    private TextField dNameField, dNoField, dAmountField, newField;

    @FXML
    private Button okButton, cancelButton, dButton;

    // -------------------------
    // Store account info
    // -------------------------
    private String name;
    private String accountNumber;
    private double balance;

    // -------------------------
    // Setter to pre-fill info
    // -------------------------
    public void setAccountInfo(String name, String accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;

        dNameField.setText(name);
        dNoField.setText(accountNumber);
        newField.setText(String.format("%.2f", balance));

        depositLabel.setText("Deposit for " + name);
    }

    // -------------------------
    // BUTTON ACTIONS
    // -------------------------
    @FXML
    private void handleOk() {
        try {
            double depositAmount = Double.parseDouble(dAmountField.getText());

            if (depositAmount <= 0) {
                showAlert("Error", "Enter an amount greater than zero.");
                return;
            }

            balance += depositAmount;
            newField.setText(String.format("%.2f", balance));

            // -------------------------
            // Update balance in database
            // -------------------------
            String updateBalanceSQL = "UPDATE customers SET balance = ? WHERE account_number = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateBalanceSQL)) {

                pstmt.setDouble(1, balance);
                pstmt.setString(2, accountNumber);
                pstmt.executeUpdate();
            }

            // -------------------------
            // Insert transaction record
            // -------------------------
            String insertTransactionSQL = "INSERT INTO transactions (account_number, type, amount, date) VALUES (?, ?, ?, ?)";
            try (Connection conn = Database.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertTransactionSQL)) {

                pstmt.setString(1, accountNumber);
                pstmt.setString(2, "Deposit");
                pstmt.setDouble(3, depositAmount);

                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                pstmt.setString(4, date);

                pstmt.executeUpdate();
            }

            showAlert("Success", "Deposited " + depositAmount + " successfully!");
            dAmountField.clear();

        } catch (NumberFormatException e) {
            showAlert("Error", "Enter a valid deposit amount.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleD() {
        Stage stage = (Stage) dButton.getScene().getWindow();
        stage.close();
    }

    // -------------------------
    // Utility method for alerts
    // -------------------------
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
