import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AccountsController {

    @FXML private Label accountsLabel;
    @FXML private TextField ourNamesField, surnameField, addressField;
    @FXML private Button backButton, savingsButton, investmentButton, chequeButton;

    private Customer customer;

    public void setCustomerInfo(Customer customer) {
        this.customer = customer;
        ourNamesField.setText(customer.getName());
        surnameField.setText(customer.getSurname());
        addressField.setText(customer.getAddress());
        accountsLabel.setText("Accounts for " + customer.getName() + " " + customer.getSurname());
    }

    @FXML private void handleBack() {
        backButton.getScene().getWindow().hide();
    }

    @FXML private void handleSavings() {
        openAccount("/fxml/Savings.fxml", "Savings Account", "setAccountInfo", 0.05);
    }

    @FXML private void handleInvestment() {
        openAccount("/fxml/Investment.fxml", "Investment Account", "setAccountInfo", 5.0);
    }

    @FXML private void handleCheque() {
        openAccount("/fxml/Cheque.fxml", "Cheque Account", "setAccountInfo", 0.0);
    }

    private void openAccount(String fxmlPath, String title, String methodName, double interest) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);

            Object controller = loader.getController();


            if (interest > 0) {

                controller.getClass()
                        .getMethod(methodName, String.class, String.class, String.class, double.class, double.class)
                        .invoke(controller, customer.getName(), customer.getAccountNumber(), "Main Branch",
                                customer.getBalance(), interest);
            } else {

                controller.getClass()
                        .getMethod(methodName, String.class, String.class, String.class, String.class, double.class)
                        .invoke(controller, customer.getName(), customer.getAccountNumber(), "Main Branch",
                                customer.getAddress(), customer.getBalance());
            }

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
