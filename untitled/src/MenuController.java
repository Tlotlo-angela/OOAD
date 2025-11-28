import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Label loginLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginsButton;

    // -------------------------
    // Handle login button click
    // -------------------------
    @FXML
    private void handleLogins() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter both username and password.");
            alert.showAndWait();
            return;
        }

        // Validate login using CustomerDAO
        Customer customer = CustomerDAO.validateLogin(username, password);

        if (customer != null) {
            try {
                // Load Main Menu FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MAINMENU.fxml"));
                Scene scene = new Scene(loader.load());

                // Pass logged-in customer to MainMenuController
                MainMenuController controller = loader.getController();
                controller.setCustomer(customer);

                Stage stage = new Stage();
                stage.setTitle("Banking System - Main Menu");
                stage.setScene(scene);
                stage.show();

                // Close login window
                loginsButton.getScene().getWindow().hide();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }
}
