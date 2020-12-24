/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mixed;

import java.net.URL;
import java.util.ResourceBundle;
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
public class RegisterController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private Label nameError;
    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnRegister;
    @FXML
    private Label confPassWordError;
    @FXML
    private CheckBox checkBoxId2;
    @FXML
    private TextField passTextField;
    @FXML
    private PasswordField passPasswordField;
    @FXML
    private CheckBox checkBoxId1;
    @FXML
    private TextField confTextField;
    @FXML
    private PasswordField confPasswordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
