/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online;

import Mixed.IpPaneController;
import Models.Player;
import Models.Records;
import history.HistoyPaneController;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import localAi.SharedController;
import prefrence.ClientData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class OnlineHistoryController implements Initializable {

    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;
    private Thread t;

    @FXML
    private Button btnOnlineBack;

    private ArrayList<Records> recieved = new ArrayList<>();

    @FXML
    private Label onlineHistoryPaneTitle;

    @FXML
    private ListView<String> recordList;

    ObservableList observableList = FXCollections.observableArrayList();

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
      
        try {
            
            closeConnections();
            Parent root = FXMLLoader.load(getClass().getResource("/online/OnlineMainPane.fxml"));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(OnlineHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleSelectedRecord(MouseEvent event) {

        int selected = recordList.getSelectionModel().getSelectedIndex();

        String firstName = recieved.get(selected).getFirstName();
        String secondName = recieved.get(selected).getSecondName();
        String firstSequence = recieved.get(selected).getFirstSeq();
        String secondSequence = recieved.get(selected).getSeconSeq();
        String theWinner = recieved.get(selected).getTheWinner();
        Records recordHistory = new Records(firstName, secondName, firstSequence, secondSequence, theWinner, theWinner);

        Models.RecordObject.setSelectedRecord(recordHistory);

        playRecordingGame(recordHistory);

    }

    
    //*************************************************************************************//
    // handle play the recording game 
    //****************************************************************************************//
    private void playRecordingGame(Records recordHistory) {
        Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
        showWinner.setTitle("PlayRecord");
        showWinner.setHeaderText(null);
        showWinner.setContentText("Play the Match of "
                + recordHistory.getFirstName() + " and  " + recordHistory.getSecondName());
        showWinner.initModality(Modality.APPLICATION_MODAL);

        ButtonType btnPlay = new ButtonType("Play");

        showWinner.getButtonTypes().setAll(btnPlay);

        Optional<ButtonType> choices = showWinner.showAndWait();
        if (choices.get() == btnPlay) {

            openPaintPage();

        }
    }

    //*************************************************************************************//
    // open paint page 
    // set parent to online
    //****************************************************************************************//
    private void openPaintPage() {
        
        SharedController.isOfflineParent=false;
        try {
            
            closeConnections();
            Parent root = FXMLLoader.load(getClass().getResource("/history/HistoryGamePane.fxml"));

            Scene scene = new Scene(root);
            Stage stage = (Stage) btnOnlineBack.getScene().getWindow();
                        stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HistoyPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initializeConn();
    }

    
    //*************************************************************************************//
    // send message to server to get online data
    //****************************************************************************************//
    private void initilization() {

        observableList.removeAll(recieved);
        recieved.clear();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "NEED_HISTORY");
        jsonObject.put("EMAIL", ClientData.getMyEmail());

        String jsonText = JSONValue.toJSONString(jsonObject);

        ps.println(jsonText);
        ps.flush();
        System.out.println("init users");

    }

    //*************************************************************************************//
    // handle initilize connection 
    //****************************************************************************************//
    private void initializeConn() {

        t=new Thread(){
            
            @Override
            public void run() {

                try {
                    mySocket = new Socket(IpPaneController.iipp, IpPaneController.PORT_NUMBER);
                    dis = new DataInputStream(mySocket.getInputStream());
                    ps = new PrintStream(mySocket.getOutputStream());
                    initilization();

                    while (true) {

                        String replyMsg;
                        replyMsg = dis.readLine();
                        handleIncomingMessage(replyMsg);
                    }

                } catch (IOException ex) {
                    //Logger.getLogger(OnlineHistoryController.class.getName()).log(Level.SEVERE, null, ex);
                    redirectToHomePage();
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }
    

    //*************************************************************************************//
    // handle incoming data 
    //****************************************************************************************//
    private void handleIncomingMessage(String jsonText) {

        try {
            Object obj = new JSONParser().parse(jsonText);
            JSONObject jo2 = (JSONObject) obj;
            String type = (String) jo2.get("TYPE");
            System.out.println("type is " + type);

            switch (type) {
                case "HISTORY_OF_GAME":
                    //someone ask to play with me
                    // handle the response
                    //   done
                    getOnlineHistory(jsonText);
                    break;
            }
        } catch (ParseException ex) {
            Logger.getLogger(OnlineHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //*************************************************************************************//
    // handle redirect to the home screen 
    //****************************************************************************************//
    private void redirectToHomePage() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    
                    Parent root = FXMLLoader.load(getClass().getResource("/localAi/HomePane.fxml"));
                    Scene scene = new Scene(root);
                    Window window = btnOnlineBack.getScene().getWindow();
                    Stage stage = (Stage) btnOnlineBack.getScene().getWindow();
                                stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


                    stage.setScene(scene);
                    stage.resizableProperty().setValue(Boolean.FALSE);

                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(OnlineHistoryController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    //*************************************************************************************//
    // handle fetch online Player 
    //****************************************************************************************//
    private void getOnlineHistory(String jsonText) {

        try {

            Object obj = new JSONParser().parse(jsonText);
            JSONObject jo2 = (JSONObject) obj;
            JSONArray jSONObject = (JSONArray) jo2.get("GAMES");
            String email = (String) jo2.get("EMAIL");

            if (email.equals(ClientData.getMyEmail())) {

                observableList.removeAll(observableList);
                observableList.removeAll(recieved);
                recieved.clear();
                for (int i = 0; i < jSONObject.size(); i++) {

                    JSONObject s = (JSONObject) jSONObject.get(i);
                    String firstEmail = (String) s.get("PLAYER1");
                    String secondEmail = (String) s.get("PLAYER2");
                    String seq1 = (String) s.get("SEQUENCE1");
                    String seq2 = (String) s.get("SEQUENCE2");
                    String winner = (String) s.get("WINNER");
                    String date = (String) s.get("DATE");
                    String firstPlayer = (String) s.get("FIRST_PLAYER");

                    recieved.add(new Records(firstEmail, secondEmail, seq1, seq2, winner, firstPlayer, date));
                    observableList.add(firstEmail + "\t\t\twith   \t  " + secondEmail + "\t\t Winner ->   " + winner + "\t\t" + date + "\t\t");

                }
                recordList.setItems(observableList);

            }

            //observableList.removeAll(lis);
        } catch (ParseException ex) {
            Logger.getLogger(OnlineHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //*************************************************************************************//
    // handle Closing the Stream 
    //****************************************************************************************//
    void closeConnections() {

        System.out.println("close Connection");
        try {
            ps.close();
            dis.close();
            mySocket.close();

        } catch (IOException ex) {
            Logger.getLogger(OnlineHistoryController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
}
