/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mixed;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author El-3ttar
 */
public class LoginController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnRegister;
    @FXML
    private CheckBox checkBoxId;
    @FXML
    private TextField passTextField;
    @FXML
    private PasswordField passPasswordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void bttnLoginHandler(ActionEvent event) {
    }

    @FXML
    private void btnBack(ActionEvent event) {
    }

    @FXML
    private void bttnRegisterHandler(ActionEvent event) {
    }

    @FXML
    private void checkBoxHandler(ActionEvent event) {
    }
    
}
