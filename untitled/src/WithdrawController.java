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

public class WithdrawController {

    @FXML private Label withdrawLabel;
    @FXML private TextField datesField, amountsField, newbieField;
    @FXML private Button okayButton, cancellationButton, wButton;

    private String name;
    private String accountNumber;
    private double balance;

    public void setAccountInfo(String name, String accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;

        withdrawLabel.setText("Withdraw from " + name);
        datesField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        newbieField.setText(String.format("%.2f", balance));
    }

    @FXML
    private void handleOkay() {
        try {
            double withdraw = Double.parseDouble(amountsField.getText());

            if (withdraw <= 0) {
                showAlert("Error", "Enter an amount greater than zero.");
                return;
            }

            if (withdraw > balance) {
                showAlert("Error", "Insufficient funds!");
                return;
            }

            balance -= withdraw;
            newbieField.setText(String.format("%.2f", balance));

            // Update database balance
            String updateBalanceSQL = "UPDATE customers SET balance = ? WHERE account_number = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateBalanceSQL)) {
                pstmt.setDouble(1, balance);
                pstmt.setString(2, accountNumber);
                pstmt.executeUpdate();
            }

            // Insert transaction
            String insertTransactionSQL = "INSERT INTO transactions (account_number, type, amount, date) VALUES (?, ?, ?, ?)";
            try (Connection conn = Database.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertTransactionSQL)) {
                pstmt.setString(1, accountNumber);
                pstmt.setString(2, "Withdraw");
                pstmt.setDouble(3, withdraw);
                pstmt.setString(4, datesField.getText());
                pstmt.executeUpdate();
            }

            showAlert("Success", "Withdrawn " + withdraw + " successfully!");
            amountsField.clear();

        } catch (NumberFormatException e) {
            showAlert("Error", "Enter a valid number.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancellation() {
        Stage stage = (Stage) cancellationButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleW() {
        Stage stage = (Stage) wButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
