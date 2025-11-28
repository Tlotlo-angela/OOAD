import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ChequeController {

    @FXML private Label chequeLabel;
    @FXML private TextField theNamesField, theAccountField, theBranchField, TheAddressField, theBalanceField;
    @FXML private Button theDepositButton, theWithdrawButton, theTransactionsButton, cButton;

    private String name;
    private String accountNumber;
    private String branch;
    private String address;
    private double balance;

    public void setAccountInfo(String name, String accountNumber, String branch, String address, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.branch = branch;
        this.address = address;
        this.balance = balance;

        theNamesField.setText(name);
        theAccountField.setText(accountNumber);
        theBranchField.setText(branch);
        TheAddressField.setText(address);
        theBalanceField.setText(String.format("%.2f", balance));

        chequeLabel.setText("Cheque Account for " + name);
    }

    @FXML private void handleTheDeposit() { openPopup("/fxml/Deposit.fxml", true); }
    @FXML private void handleTheWithdraw() { openPopup("/fxml/Withdraw.fxml", true); }
    @FXML private void handleTheTransactions() { openPopup("/fxml/Transactions.fxml", false); }
    @FXML private void handleC() { cButton.getScene().getWindow().hide(); }

    private void openPopup(String fxmlPath, boolean refreshAfter) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pass account info to popup
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
            theBalanceField.setText(String.format("%.2f", balance));
        }
    }
}
