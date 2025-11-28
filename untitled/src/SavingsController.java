import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SavingsController {

    @FXML private Label savingsLabel;
    @FXML private TextField myNamesField, myAccountField, myBranchField, myBalanceField, myInterestField;
    @FXML private Button myDepositButton, myWithdrawButton, myTransactionsButton, sButton;

    private String name;
    private String accountNumber;
    private String branch;
    private double balance;
    private double interest;

    public void setAccountInfo(String name, String accountNumber, String branch, double balance, double interest) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.branch = branch;
        this.balance = balance;
        this.interest = interest;

        myNamesField.setText(name);
        myAccountField.setText(accountNumber);
        myBranchField.setText(branch);
        myBalanceField.setText(String.format("%.2f", balance));
        myInterestField.setText(String.format("%.2f", interest));

        savingsLabel.setText("Savings Account for " + name);
    }

    @FXML private void handleMyDeposit() { openPopup("/fxml/Deposit.fxml", true); }
    @FXML private void handleMyWithdraw() { openPopup("/fxml/Withdraw.fxml", true); }
    @FXML private void handleMyTransactions() { openPopup("/fxml/Transactions.fxml", false); }
    @FXML private void handleS() { sButton.getScene().getWindow().hide(); }

    private void openPopup(String fxmlPath, boolean refreshAfter) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);

            Object controller = loader.getController();
            try {
                controller.getClass()
                        .getMethod("setAccountInfo", String.class, String.class, double.class)
                        .invoke(controller, name, accountNumber, balance);
            } catch (NoSuchMethodException e) {
                controller.getClass()
                        .getMethod("setAccountInfo", String.class, String.class)
                        .invoke(controller, name, accountNumber);
            }

            stage.showAndWait();

            if (refreshAfter) refreshBalance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshBalance() {
        Customer customer = CustomerDAO.getCustomerByAccountNumber(accountNumber);
        if (customer != null) {
            balance = customer.getBalance();
            myBalanceField.setText(String.format("%.2f", balance));
        }
    }
}
