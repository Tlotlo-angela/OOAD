import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Label menuLabel;

    @FXML
    private Button transactionButton, accountsButton, depoButton, withButton, logoutButton,
            savingsButton, investmentButton, chequeButton;

    private Customer customer;

    /**
     * Set the logged-in customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
        menuLabel.setText("Welcome, " + customer.getName() + " " + customer.getSurname());
    }

    // -------------------------
    // Open Accounts Window
    // -------------------------
    @FXML
    private void handleAccounts() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Accounts.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            AccountsController controller = loader.getController();
            controller.setCustomerInfo(customer);

            stage.setTitle("Accounts");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------
    // Open Savings Account Window
    // -------------------------
    @FXML
    private void handleSavings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Savings.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            SavingsController controller = loader.getController();
            controller.setAccountInfo(
                    customer.getName(),
                    customer.getAccountNumber(),
                    "Main Branch", // Example branch
                    customer.getBalance(),
                    0.05 // Example interest
            );

            stage.setTitle("Savings Account");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------
    // Open Investment Account Window
    // -------------------------
    @FXML
    private void handleInvestment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Investment.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            InvestmentController controller = loader.getController();
            controller.setAccountInfo(
                    customer.getName(),
                    customer.getAccountNumber(),
                    "Main Branch",
                    customer.getBalance(),
                    5.0 // Example interest
            );

            stage.setTitle("Investment Account");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------
    // Open Cheque Account Window
    // -------------------------
    @FXML
    private void handleCheque() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Cheque.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            ChequeController controller = loader.getController();
            controller.setAccountInfo(
                    customer.getName(),
                    customer.getAccountNumber(),
                    "Main Branch",
                    customer.getAddress(),
                    customer.getBalance()
            );

            stage.setTitle("Cheque Account");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------
    // Open Deposit Window
    // -------------------------
    @FXML
    private void handleDepo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Deposit.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            DepositController controller = loader.getController();
            controller.setAccountInfo(
                    customer.getName(),
                    customer.getAccountNumber(),
                    customer.getBalance()
            );

            stage.setTitle("Deposit");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------
    // Open Withdraw Window
    // -------------------------
    @FXML
    private void handleWith() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Withdraw.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            WithdrawController controller = loader.getController();
            controller.setAccountInfo(
                    customer.getName(),
                    customer.getAccountNumber(),
                    customer.getBalance()
            );

            stage.setTitle("Withdraw");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------
    // Open Transactions Window
    // -------------------------
    @FXML
    private void handleTransaction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transactions.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            TransactionsController controller = loader.getController();
            controller.setAccountInfo(customer.getName(), customer.getAccountNumber());

            stage.setTitle("Transactions");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------
    // Logout
    // -------------------------
    @FXML
    private void handleLogout() {
        logoutButton.getScene().getWindow().hide();
    }
}
