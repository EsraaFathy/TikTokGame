package Mixed;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import online.OnlineMainPaneController;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RegisterBase extends AnchorPane {

    private String nameText, emailText, passwordtext, passwordConfText;
    boolean isName, isEmail, isPassword, isPasscon;
    Socket client;
    DataInputStream dis;
    PrintStream ps;

    private Thread t;

    String type, key;
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
    protected final Label label2;
    protected final TextField nameField;
    protected final TextField emailField;
    protected final Label nameError;
    protected final Label emailError;
    protected final Label passwordError;
    protected final Button btnBack;
    protected final Glow glow;
    protected final Lighting lighting;
    protected final Button btnRegister;
    protected final Glow glow0;
    protected final Lighting lighting0;
    protected final Label label3;
    protected final Label confPassWordError;
    protected final CheckBox checkBoxId2;
    protected final StackPane stackPane;
    protected final TextField passTextField;
    protected final PasswordField passPasswordField;
    protected final CheckBox checkBoxId1;
    protected final StackPane stackPane0;
    protected final TextField confTextField;
    protected final PasswordField confPasswordField;

    public RegisterBase() {

        pane = new Pane();
        imageView = new ImageView();
        imageView0 = new ImageView();
        gaussianBlur = new GaussianBlur();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        nameField = new TextField();
        emailField = new TextField();
        nameError = new Label();
        emailError = new Label();
        passwordError = new Label();
        btnBack = new Button();
        glow = new Glow();
        lighting = new Lighting();
        btnRegister = new Button();
        glow0 = new Glow();
        lighting0 = new Lighting();
        label3 = new Label();
        confPassWordError = new Label();
        checkBoxId2 = new CheckBox();
        stackPane = new StackPane();
        passTextField = new TextField();
        passPasswordField = new PasswordField();
        checkBoxId1 = new CheckBox();
        stackPane0 = new StackPane();
        confTextField = new TextField();
        confPasswordField = new PasswordField();

        setId("AnchorPane");
        setPrefHeight(736.0);
        setPrefWidth(1189.0);

        pane.setPrefHeight(736.0);
        pane.setPrefWidth(1189.0);

        imageView.setFitHeight(770.0);
        imageView.setFitWidth(1199.0);
        imageView.setLayoutY(-8.0);
        imageView.setPickOnBounds(true);
        imageView.setImage(new Image(getClass().getResource("/img/7.jpg").toExternalForm()));

        imageView0.setDisable(true);
        imageView0.setFitHeight(574.0);
        imageView0.setFitWidth(691.0);
        imageView0.setLayoutX(63.0);
        imageView0.setLayoutY(95.0);
        imageView0.setPickOnBounds(true);
        imageView0.setImage(new Image(getClass().getResource("/img/l.jpg").toExternalForm()));

        imageView0.setEffect(gaussianBlur);

        label.setLayoutX(326.0);
        label.setLayoutY(119.0);
        label.setPrefHeight(40.0);
        label.setPrefWidth(229.0);
        label.setText("Sign Up");
        label.setTextFill(javafx.scene.paint.Color.valueOf("#eb0c0c"));
        label.setFont(new Font("System Bold", 32.0));

        label0.setLayoutX(85.0);
        label0.setLayoutY(204.0);
        label0.setPrefHeight(30.0);
        label0.setPrefWidth(200.0);
        label0.setText("Enter Your Name");
        label0.setStyle("-fx-text-fill: #caeb0e;");
        //label0.setTextFill(javafx.scene.paint.Color.valueOf("#342ee8"));
        label0.setFont(new Font(20.0));

        label1.setLayoutX(85.0);
        label1.setLayoutY(291.0);
        label1.setPrefHeight(30.0);
        label1.setPrefWidth(200.0);
        label1.setText("Enter Your Email");
        label1.setStyle("-fx-text-fill: #caeb0e;");
        //label1.setTextFill(javafx.scene.paint.Color.valueOf("#342ee8"));
        label1.setFont(new Font(20.0));

        label2.setLayoutX(85.0);
        label2.setLayoutY(392.0);
        label2.setPrefHeight(30.0);
        label2.setPrefWidth(200.0);
        label2.setText("Enter Your Password");
        label2.setStyle("-fx-text-fill: #caeb0e;");
        //label2.setTextFill(javafx.scene.paint.Color.valueOf("#342ee8"));
        label2.setFont(new Font(20.0));

        nameField.setLayoutX(326.0);
        nameField.setLayoutY(206.0);
        nameField.setPrefHeight(38.0);
        nameField.setPrefWidth(395.0);

        emailField.setLayoutX(326.0);
        emailField.setLayoutY(295.0);
        emailField.setPrefHeight(38.0);
        emailField.setPrefWidth(395.0);

        nameError.setLayoutX(326.0);
        nameError.setLayoutY(255.0);
        nameError.setPrefHeight(26.0);
        nameError.setPrefWidth(395.0);
        nameError.setStyle("-fx-text-fill: #F10B29;");
        nameError.setTextFill(javafx.scene.paint.Color.valueOf("#e11515"));
        nameError.setFont(new Font(15.0));

        emailError.setLayoutX(326.0);
        emailError.setLayoutY(342.0);
        emailError.setPrefHeight(26.0);
        emailError.setPrefWidth(395.0);
        emailError.setStyle("-fx-text-fill: #F10B29;");
        emailError.setTextFill(javafx.scene.paint.Color.valueOf("#da1414"));
        emailError.setFont(new Font(15.0));

        passwordError.setLayoutX(326.0);
        passwordError.setLayoutY(445.0);
        passwordError.setPrefHeight(26.0);
        passwordError.setPrefWidth(259.0);
        passwordError.setStyle("-fx-text-fill: #F10B29;");
        passwordError.setTextFill(javafx.scene.paint.Color.valueOf("#e11414"));
        passwordError.setFont(new Font(15.0));

        btnBack.setCancelButton(true);
        btnBack.setLayoutX(25.0);
        btnBack.setLayoutY(22.0);
        btnBack.setMnemonicParsing(false);
        btnBack.setPrefHeight(44.0);
        btnBack.setPrefWidth(71.0);
        btnBack.setStyle("-fx-background-color: #141717; -fx-background-radius: 20;");
        btnBack.setText("<--");
        btnBack.setTextFill(javafx.scene.paint.Color.valueOf("#f21111"));
        btnBack.setFont(new Font("System Bold", 19.0));
        glow.setInput(lighting);
        btnBack.setEffect(glow);


        btnRegister.setDefaultButton(true);
        btnRegister.setLayoutX(304.0);
        btnRegister.setLayoutY(584.0);
        btnRegister.setMnemonicParsing(false);
        btnRegister.setPrefHeight(36.0);
        btnRegister.setPrefWidth(208.0);
        btnRegister.setStyle("-fx-background-color: #141717; -fx-background-radius: 20;");
        btnRegister.setText("Register");
        btnRegister.setTextFill(javafx.scene.paint.Color.valueOf("#cce011"));
        btnRegister.setFont(new Font("System Bold", 25.0));

        glow0.setInput(lighting0);
        btnRegister.setEffect(glow0);

        label3.setLayoutX(85.0);
        label3.setLayoutY(486.0);
        label3.setPrefHeight(30.0);
        label3.setPrefWidth(200.0);
        label3.setText("Confirm The Password");
        label3.setStyle("-fx-text-fill: #caeb0e;");
       // label3.setTextFill(javafx.scene.paint.Color.valueOf("#342ee8"));
        label3.setFont(new Font(20.0));

        confPassWordError.setLayoutX(326.0);
        confPassWordError.setLayoutY(530.0);
        confPassWordError.setPrefHeight(26.0);
        confPassWordError.setPrefWidth(259.0);
        confPassWordError.setStyle("-fx-text-fill: #F10B29;");
        confPassWordError.setTextFill(javafx.scene.paint.Color.valueOf("#e11414"));
        confPassWordError.setFont(new Font(15.0));

        checkBoxId2.setLayoutX(631.0);
        checkBoxId2.setLayoutY(530.0);
        checkBoxId2.setMnemonicParsing(false);
        checkBoxId2.setPrefHeight(26.0);
        checkBoxId2.setPrefWidth(123.0);
        checkBoxId2.setText("Password");

        stackPane.setLayoutX(326.0);
        stackPane.setLayoutY(384.0);
        stackPane.setPrefHeight(44.0);
        stackPane.setPrefWidth(395.0);

        passTextField.setPrefHeight(44.0);
        passTextField.setPrefWidth(395.0);

        passPasswordField.setPrefHeight(42.0);
        passPasswordField.setPrefWidth(395.0);

        checkBoxId1.setLayoutX(631.0);
        checkBoxId1.setLayoutY(445.0);
        checkBoxId1.setMnemonicParsing(false);
        checkBoxId1.setPrefHeight(26.0);
        checkBoxId1.setPrefWidth(123.0);
        checkBoxId1.setText("Password");

        stackPane0.setLayoutX(326.0);
        stackPane0.setLayoutY(478.0);
        stackPane0.setPrefHeight(47.0);
        stackPane0.setPrefWidth(395.0);

        confTextField.setPrefHeight(38.0);
        confTextField.setPrefWidth(395.0);

        confPasswordField.setPrefHeight(38.0);
        confPasswordField.setPrefWidth(395.0);

        getChildren().add(pane);
        getChildren().add(imageView);
        getChildren().add(imageView0);
        getChildren().add(label);
        getChildren().add(label0);
        getChildren().add(label1);
        getChildren().add(label2);
        getChildren().add(nameField);
        getChildren().add(emailField);
        getChildren().add(nameError);
        getChildren().add(emailError);
        getChildren().add(passwordError);
        getChildren().add(btnBack);
        getChildren().add(btnRegister);
        getChildren().add(label3);
        getChildren().add(confPassWordError);
        getChildren().add(checkBoxId2);
        stackPane.getChildren().add(passTextField);
        stackPane.getChildren().add(passPasswordField);
        getChildren().add(stackPane);
        getChildren().add(checkBoxId1);
        stackPane0.getChildren().add(confTextField);
        stackPane0.getChildren().add(confPasswordField);
        getChildren().add(stackPane0);

        checkBoxId1.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            String temp;
            if (checkBoxId1.isSelected()) {
                temp = passPasswordField.getText();
                passTextField.setText(temp);
                passTextField.toFront();
            } else {
                temp = passTextField.getText();
                passPasswordField.setText(passTextField.getText());
                passPasswordField.toFront();
            }
            // passwordtext=temp;

        }));
        checkBoxId2.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (checkBoxId2.isSelected()) {
                confTextField.setText(confPasswordField.getText());
                confTextField.toFront();
            } else {
                confPasswordField.setText(confTextField.getText());
                confPasswordField.toFront();
            }

        }));

        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getData();
                //isIp = validateIp(ipText);
                isName = validateName(nameText);
                isEmail = validateEmail(emailText);
                isPassword = validatePassword(passwordtext);

                isPasscon = confPass(passwordConfText);

                if (!isName || !isEmail || !isPassword || !isPasscon) {
                } else {

                    connectServer(nameText, emailText, passwordtext);
                    //key = "SUCCESS";

                }

            }

        });
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Manager.viewPane(Manager.login);
                clear();

            }

        });

    }

    void clear() {
        nameField.clear();
        nameError.setText("");
        emailField.clear();
        emailError.setText("");
        passPasswordField.clear();
        passwordError.setText("");
        confPasswordField.clear();
        confPassWordError.setText("");
//        ipField.clear();
//        ipError.setText("");
    }

    void getData() {
        nameText = nameField.getText();
        emailText = emailField.getText();
        passwordtext = !checkBoxId1.isSelected() ? passPasswordField.getText() : passTextField.getText();
        passwordConfText = !checkBoxId2.isSelected() ? confPasswordField.getText() : confTextField.getText();
        //  ipText = ipField.getText();

    }

    boolean validateEmail(String email) {
        final String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (email.isEmpty() || !matcher.matches()) {
            emailError.setText("Email isn't correct");
            return false;
        } else {
            emailError.setText("");
            return true;
        }

    }

    boolean validateName(String name) {
        final String namePattern = "[A-Za-z0-9]+";
        Pattern pattern = Pattern.compile(namePattern);
        Matcher matcher = pattern.matcher(name);

        if (name.isEmpty() || !matcher.matches()) {
            nameError.setText("your name must be valid and must has length between 6 and 30 char");
            return false;
        } else {
            nameError.setText("");
            return true;
        }

    }

    boolean validatePassword(String pass) {

        if (pass.isEmpty() || pass.trim().length() < 8) {
            passwordError.setText("password must be greater than 8 char");
            return false;
        } else {
            passwordError.setText("");
            return true;
        }

    }

//    boolean validateIp(String ip) {
//        String zeroTo255
//                = "(\\d{1,2}|(0|1)\\"
//                + "d{2}|2[0-4]\\d|25[0-5])";
//        String regex
//                = zeroTo255 + "\\."
//                + zeroTo255 + "\\."
//                + zeroTo255 + "\\."
//                + zeroTo255;
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(ip);
//
//        if (!matcher.matches()) {
//            ipError.setText("IP address must be valid");
//            return false;
//        } else {
//            ipError.setText("");
//            return true;
//        }
//
//    }
    boolean confPass(String pass) {

        if (!pass.equals(passwordtext)) {
            confPassWordError.setText("two password must be identical");
            return false;
        } else {
            confPassWordError.setText("");
            return true;
        }

    }

    void connectServer(String name, String email, String password) {

        t = new Thread() {
            @Override
            public void run() {
                try {

                    client = new Socket(IpPaneController.iipp, IpPaneController.PORT_NUMBER);
                    dis = new DataInputStream(client.getInputStream());
                    ps = new PrintStream(client.getOutputStream());
                    jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "REGISTER");
                    jsonObject.put("NAME", name);
                    jsonObject.put("EMAIL", email);
                    jsonObject.put("PASSWORD", password);
                    jsonText = JSONValue.toJSONString(jsonObject);
                    ps.println(jsonText);
                    ps.flush();
                    inComing = dis.readLine();
                    obj = new JSONParser().parse(inComing);
                    jo2 = (JSONObject) obj;
                    type = (String) jo2.get("TYPE");
                    key = (String) jo2.get("KEY");

                    System.out.println(inComing);
                    System.out.println(type);
                    System.out.println(key);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            switch (key) {
                                case "SUCCESS":
                                    
                                    closeConnections();
                                    t.stop();
                                    Alert alretSuccess = new Alert(Alert.AlertType.NONE, "you are successfully registered", ButtonType.OK);
                                    alretSuccess.setTitle("Succes");
                                    alretSuccess.showAndWait();
                                    Manager.viewPane(Manager.login);
                                    clear();
                                    System.out.println("Moved");
                                    break;
                                case "EMAIL_IS_USED_BEFORE":
                                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                                    alertError.setTitle("Error ");
                                    alertError.setHeaderText("Email is already registered");
                                    alertError.showAndWait();
                                    break;

                            }
                        }
                    });
                } catch (IOException ex) {

                    stop();
                    // Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(RegisterBase.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
//                    closing();
                }

            }
        };
        t.setDaemon(true);
        t.start();

    }
    void closeConnections() {

        System.out.println("close Connection");
        try {
            ps.close();
            dis.close();
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(RegisterBase.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}
