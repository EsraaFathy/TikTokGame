package Mixed;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import online.OnlineMainPaneController;
import prefrence.ClientData;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoginBase extends AnchorPane {

    private String emailText, passwordtext;
    boolean _isEmail, _isPassword;
    private Socket client;
    private DataInputStream dis;
    private PrintStream ps;

    private Thread th;
    String _type, _key;
    JSONObject jsonObject;
    String jsonText;
    String inComing;
    Object obj;
    JSONObject jo2;

    protected final Pane pane;
    protected final ImageView imageView;
    protected final ImageView imageView0;
    protected final GaussianBlur gaussianBlur;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final TextField emailField;
    protected final Label emailError;
    protected final Label passwordError;
    protected final Button btnLogin;
    protected final Glow glow;
    protected final Lighting lighting;
    protected final Button btnBack;
    protected final Glow glow0;
    protected final Lighting lighting0;
    protected final Button btnRegister;
    protected final Glow glow1;
    protected final Lighting lighting1;
    protected final CheckBox checkBoxId;
    protected final StackPane stackPane;
    protected final TextField passTextField;
    protected final PasswordField passPasswordField;

    public LoginBase() {

        pane = new Pane();
        imageView = new ImageView();
        imageView0 = new ImageView();
        gaussianBlur = new GaussianBlur();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        emailField = new TextField();
        emailError = new Label();
        passwordError = new Label();
        btnLogin = new Button();
        glow = new Glow();
        lighting = new Lighting();
        btnBack = new Button();
        glow0 = new Glow();
        lighting0 = new Lighting();
        btnRegister = new Button();
        glow1 = new Glow();
        lighting1 = new Lighting();
        checkBoxId = new CheckBox();
        stackPane = new StackPane();
        passTextField = new TextField();
        passPasswordField = new PasswordField();

        setId("AnchorPane");
        setPrefHeight(736.0);
        setPrefWidth(1189.0);

        pane.setPrefHeight(736.0);
        pane.setPrefWidth(1189.0);

        imageView.setDisable(true);
        imageView.setFitHeight(765.0);
        imageView.setFitWidth(1199.0);
        imageView.setPickOnBounds(true);
        imageView.setImage(new Image(getClass().getResource("/img/7.jpg").toExternalForm()));

        imageView0.setDisable(true);
        imageView0.setFitHeight(397.0);
        imageView0.setFitWidth(692.0);
        imageView0.setLayoutX(60.0);
        imageView0.setLayoutY(133.0);
        imageView0.setPickOnBounds(true);
        imageView0.setImage(new Image(getClass().getResource("/img/l.jpg").toExternalForm()));

        imageView0.setEffect(gaussianBlur);

        label.setLayoutX(348.0);
        label.setLayoutY(150.0);
        label.setPrefHeight(40.0);
        label.setPrefWidth(166.0);
        label.setText("Login");
        label.setStyle("-fx-text-fill:#f0d30a;");
        label.setFont(new Font("System Bold", 32.0));

        label0.setLayoutX(80.0);
        label0.setLayoutY(251.0);
        label0.setPrefHeight(30.0);
        label0.setPrefWidth(200.0);
        label0.setText("Enter Your Email");
        label0.setTextFill(javafx.scene.paint.Color.valueOf("#d2ee17"));
        label0.setFont(new Font("System Bold", 21.0));

        label1.setLayoutX(80.0);
        label1.setLayoutY(346.0);
        label1.setPrefHeight(30.0);
        label1.setPrefWidth(200.0);
        label1.setText("Enter Your Password");
        label1.setTextFill(javafx.scene.paint.Color.valueOf("#d2ee17"));
        label1.setFont(new Font("System Bold", 20.0));

        emailField.setLayoutX(281.0);
        emailField.setLayoutY(247.0);
        emailField.setPrefHeight(38.0);
        emailField.setPrefWidth(395.0);

        emailError.setLayoutX(281.0);
        emailError.setLayoutY(293.0);
        emailError.setPrefHeight(31.0);
        emailError.setPrefWidth(395.0);
        emailError.setTextFill(javafx.scene.paint.Color.valueOf("#e11515"));
        emailError.setFont(new Font(15.0));

        passwordError.setLayoutX(281.0);
        passwordError.setLayoutY(392.0);
        passwordError.setPrefHeight(31.0);
        passwordError.setPrefWidth(364.0);
        passwordError.setTextFill(javafx.scene.paint.Color.valueOf("#da1414"));
        passwordError.setFont(new Font(15.0));

        btnLogin.setDefaultButton(true);
        btnLogin.setLayoutX(445.0);
        btnLogin.setLayoutY(583.0);
        btnLogin.setMnemonicParsing(false);
        //btnLogin.setOnAction(this::bttnLoginHandler);
        btnLogin.setPrefHeight(67.0);
        btnLogin.setPrefWidth(200.0);
        btnLogin.setStyle("-fx-background-color: #141717; -fx-background-radius: 40;-fx-text-fill:#f0d30a;");
        btnLogin.setText("Login");
        btnLogin.setFont(new Font("System Bold", 29.0));

        lighting.setDiffuseConstant(0.93);
        lighting.setSpecularConstant(0.99);
        lighting.setSpecularExponent(29.75);
        lighting.setSurfaceScale(4.18);
        glow.setInput(lighting);
        btnLogin.setEffect(glow);

        btnBack.setCancelButton(true);
        btnBack.setLayoutX(29.0);
        btnBack.setLayoutY(26.0);
        btnBack.setMnemonicParsing(false);
        //  btnBack.setOnAction(this::btnBack);
        btnBack.setPrefHeight(38.0);
        btnBack.setPrefWidth(71.0);
        btnBack.setStyle("-fx-background-color: #141717; -fx-background-radius: 20;-fx-text-fill:#f0d30a;");
        btnBack.setText("<--");
        btnBack.setTextFill(javafx.scene.paint.Color.valueOf("#f8f002"));
        btnBack.setFont(new Font("System Bold", 19.0));

        glow0.setInput(lighting0);
        btnBack.setEffect(glow0);

        btnRegister.setLayoutX(158.0);
        btnRegister.setLayoutY(583.0);
        btnRegister.setMnemonicParsing(false);
        //    btnRegister.setOnAction(this::bttnRegisterHandler);
        btnRegister.setPrefHeight(67.0);
        btnRegister.setPrefWidth(210.0);
        btnRegister.setStyle("-fx-background-color: #141717; -fx-background-radius: 40;-fx-text-fill:#f0d30a;");
        btnRegister.setText("Register");
        btnRegister.setFont(new Font("System Bold", 28.0));

        lighting1.setDiffuseConstant(1.15);
        lighting1.setSpecularConstant(0.56);
        lighting1.setSpecularExponent(32.69);
        lighting1.setSurfaceScale(6.25);
        glow1.setInput(lighting1);
        btnRegister.setEffect(glow1);

        checkBoxId.setLayoutX(645.0);
        checkBoxId.setLayoutY(385.0);
        checkBoxId.setMnemonicParsing(false);
//        checkBoxId.setOnAction(this::checkBoxHandler);
        checkBoxId.setPrefHeight(31.0);
        checkBoxId.setPrefWidth(95.0);
        checkBoxId.setText("password");
        checkBoxId.setTextFill(javafx.scene.paint.Color.valueOf("#f2f0f0"));

        stackPane.setLayoutX(281.0);
        stackPane.setLayoutY(341.0);
        stackPane.setPrefHeight(40.0);
        stackPane.setPrefWidth(395.0);

        passTextField.setPrefHeight(38.0);
        passTextField.setPrefWidth(395.0);

        passPasswordField.setPrefHeight(38.0);
        passPasswordField.setPrefWidth(395.0);

        pane.getChildren().add(imageView);
        pane.getChildren().add(imageView0);
        pane.getChildren().add(label);
        pane.getChildren().add(label0);
        pane.getChildren().add(label1);
        pane.getChildren().add(emailField);
        pane.getChildren().add(emailError);
        pane.getChildren().add(passwordError);
        pane.getChildren().add(btnLogin);
        pane.getChildren().add(btnBack);
        pane.getChildren().add(btnRegister);
        pane.getChildren().add(checkBoxId);
        stackPane.getChildren().add(passTextField);
        stackPane.getChildren().add(passPasswordField);
        pane.getChildren().add(stackPane);
        getChildren().add(pane);

        //////////////////////////////////////////
        //**when checkbox is selected the password become visible
        //////////////////////////////////////////
        checkBoxId.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (checkBoxId.isSelected()) {
                passTextField.setText(passPasswordField.getText());
                passTextField.toFront();
            } else {
                passPasswordField.setText(passTextField.getText());
                passPasswordField.toFront();
            }

        }));

        ////when login button is pressed
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ////////
                //**get data from text field
                ////////
                _getData();
                _isEmail = _validateEmail(emailText);
                _isPassword = _validatePassword(passwordtext);
                if (!_isEmail || !_isPassword) {
                    //there is error in validation
                } else {
                    //validation is succeeded
                    _connectServer(emailText, passwordtext);
                }
            }
        });
        
        
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Manager.viewPane(Manager.signup);
                _clear();

            }

        });
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Parent root;
                try {
                    _clear();
                    root = FXMLLoader.load(getClass().getResource("/localAi/HomePane.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setScene(scene);
                    stage.resizableProperty().setValue(Boolean.FALSE);

                    stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(LoginBase.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

    }

    ////////////////////////
    //clear text fields
    ////////////////////////
    void _clear() {
        emailField.clear();
        emailError.setText("");
        passPasswordField.clear();
        passwordError.setText("");

    }

    ////////////////////////
    //get data from textfields
    ////////////////////////
    void _getData() {
        emailText = emailField.getText();
        passwordtext = !checkBoxId.isSelected() ? passPasswordField.getText() : passTextField.getText();
        //  _ipText = ipField.getText();

    }

    ////////////////////////
    //check if email is valid or not
    ////////////////////////
    boolean _validateEmail(String email) {
        final String _emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(_emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (email.isEmpty() || !matcher.matches()) {
            emailError.setText("Email isn't correct");
            return false;
        } else {
            emailError.setText("");
            return true;
        }

    }

    ////////////////////////
    //check if password is valid or not
    ////////////////////////
    boolean _validatePassword(String pass) {
        if (pass.isEmpty() || pass.trim().length() < 8) {
            passwordError.setText("password must be greater than 8 char");
            return false;
        } else {
            passwordError.setText("");
            return true;
        }

    }

    void _connectServer(String email, String password) {

        th = new Thread() {
            @Override
            public void run() {
                try {

                    //connect to the server
                    System.out.println("enter connectSerever");
                    client = new Socket(IpPaneController.iipp, 5005);
                    dis = new DataInputStream(client.getInputStream());
                    ps = new PrintStream(client.getOutputStream());

                    //prepare json
                    jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "LOGIN");
                    jsonObject.put("EMAIL", email);
                    jsonObject.put("PASSWORD", password);
                    jsonText = JSONValue.toJSONString(jsonObject);

                    //write json into stream
                    ps.println(jsonText);
                    System.out.println(jsonText);
                    ps.flush();

                    //read from stream
                    inComing = dis.readLine();
                    System.out.println(inComing);
                    obj = new JSONParser().parse(inComing);
                    jo2 = (JSONObject) obj;
                    _type = (String) jo2.get("TYPE");
                    _key = (String) jo2.get("KEY");

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            switch (_key) {
                                case "SUCCESS": {
                                    try {
                                        inComing = dis.readLine();
                                        System.out.println(inComing);
                                    } catch (IOException ex) {
                                        Logger.getLogger(LoginBase.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Login");
                                alert.setHeaderText(null);
                                alert.setContentText("Login Successfully");
                                alert.initModality(Modality.APPLICATION_MODAL);

                                //when client login i save his email
                                ClientData.setMyEmail(emailText);
                                ButtonType btnOk = new ButtonType("ok");

                                alert.getButtonTypes().setAll(btnOk);

                                Optional<ButtonType> choices = alert.showAndWait();

                                 {
                                    System.out.println("got Online screen screen");
                                    Parent root;
                                    try {
                                        _clear();
                                        root = FXMLLoader.load(getClass().getResource("/online/OnlineMainPane.fxml"));
                                        Scene scene = new Scene(root);
                                        Window window = label.getScene().getWindow();
                                        Stage stage = (Stage) label.getScene().getWindow();
                                        stage.setScene(scene);
                                        stage.resizableProperty().setValue(Boolean.FALSE);
                                        stage.show();

                                        closeConnections();
                                        th.stop();
                                    } catch (IOException ex) {
                                        Logger.getLogger(LoginBase.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }

                                break;
                                case "INCORRECT_PASSWORD":
                                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                                    alert1.setTitle("Login Erorr");
                                    alert1.setContentText("The Passwprd you Entered is Wrong ");
                                    alert1.showAndWait();
                                    break;
                                default:
                                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                    alert2.setTitle("Login Erorr");
                                    alert2.setContentText("Email Not found \n or you Mayn't Register ");
                                    alert2.showAndWait();
                            }
                        }
                    });

                } catch (IOException ex) {
                    ///when there is a problem in server
                    th.stop();

                } catch (ParseException ex) {
                    Logger.getLogger(LoginBase.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        th.setDaemon(true);
        th.start();

    }

    void closeConnections() {

        System.out.println("close Connection");
        try {
            ps.close();
            dis.close();
            client.close();

        } catch (IOException ex) {
            Logger.getLogger(LoginBase.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
