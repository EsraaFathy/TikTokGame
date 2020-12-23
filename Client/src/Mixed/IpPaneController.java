/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mixed;

import history.HistoryGamePaneController;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import localAi.SharedController;
import online.OnlineMainPaneController;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class IpPaneController implements Initializable {

    public static String iipp;
    public final static int PORT_NUMBER=5005;
    private static String ip;

    private Socket client;
    private DataInputStream dis;
    private PrintStream ps;

    @FXML

    private TextField txt_ip;

    @FXML
    private Label ipError;

    @FXML
    private Button btn_check_ip;
    
    @FXML
    private Button btnBack;
    
    
    @FXML
    private void handleBtnBack(ActionEvent event)
    {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/localAi/HomePane.fxml"));
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/img/download.png").toExternalForm()));
            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            
            stage.show();

            
        } catch (IOException ex) {
            Logger.getLogger(HistoryGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleCheckIp(ActionEvent event) {
        System.out.println("btn clicked");
        ip = txt_ip.getText();
        boolean isIp = validateIp(ip);
        btn_check_ip.setText("Plaese Wait ....");
        btn_check_ip.setDisable(true);
        if (!isIp) {
            btn_check_ip.setText("Connect");
            btn_check_ip.setDisable(false);

        } else {

            btn_check_ip.setDisable(false);

            // ClientSocket.ip = _ipText;
            //ClientSocket.btn = btnConnect;
            //ClientSocket.ipTextField = ipTextField;
            //ClientSocket.getInstance();  
            Initialization(ip);

        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void Initialization(String ip) {
        new Thread() {
            @Override
            public void run() {
                try {

                    btn_check_ip.setDisable(true);
                    System.out.println("try to connect to server");
                    client = new Socket(ip,PORT_NUMBER);
                    dis = new DataInputStream(client.getInputStream());
                    ps = new PrintStream(client.getOutputStream());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            System.out.println("connection ocurr");

                            iipp = ip;

                            alertConnected();
                        }
                    });

                } catch (IOException ex) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("connection failed");
                            alertFailed();
                        }
                    });

                    stop();
                    Logger.getLogger(IpPaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    public void alertFailed() {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error in connection ");
        alertError.setHeaderText("please ,enter the accurate ip of the server");
        alertError.showAndWait();
        btn_check_ip.setDisable(false);
        btn_check_ip.setText("Try Again ....");

    }

    boolean validateIp(String ip) {
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        if (!matcher.matches()) {
            ipError.setText("IP address must be valid");
            btn_check_ip.setDisable(true);
            return false;
        } else {
            ipError.setText("");
            btn_check_ip.setDisable(true);

            return true;
        }
    }

    public void alertConnected() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connection");
        alert.setHeaderText(null);
        alert.setContentText("connection is achieved");
        alert.initModality(Modality.APPLICATION_MODAL);
        

        ButtonType btnOk = new ButtonType("ok");

        alert.getButtonTypes().setAll(btnOk);

        Optional<ButtonType> choices = alert.showAndWait();
        //if (choices.get() == btnOk  ) 
        {
            closeConnections();

            // open new screen
            Pane root = new Pane();

            root.getChildren().add(Manager.login);
            root.getChildren().add(Manager.signup);

            Manager.viewPane(Manager.login);

            //Manager.viewPane(Manager.login);
            Scene scene = new Scene(root);

            Window window = btn_check_ip.getScene().getWindow();
            Stage stage = (Stage) btn_check_ip.getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();

        }
    }
    
        void closeConnections() {

        System.out.println("close Connection");
        try {
            ps.close();
            dis.close();
            client.close();

        } catch (IOException ex) {
            Logger.getLogger(IpPaneController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }


}
