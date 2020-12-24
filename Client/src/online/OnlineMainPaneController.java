package online;

import Mixed.IpPaneController;
import Mixed.Manager;
import Mixed.RegisterBase;
import Models.Player;
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
import javafx.event.EventHandler;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
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
public class OnlineMainPaneController implements Initializable {
 

    private Socket mySocket;
    private DataInputStream dis;
    private PrintStream ps;

    private boolean key = false;

    private ArrayList<Player> lis = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label onlineMainPaneTitle;
    @FXML
    private Button btn_onlineLogout;
    @FXML
    private Button btn_onlineSeeHistory;

    @FXML
    private ListView<String> playerList;

    ObservableList observableList = FXCollections.observableArrayList();

    private Thread t;

    //***************************************************************************//
    // handle logout
    //****************************************************************************************//
    @FXML
    private void handleLogoutAction(ActionEvent event) {
        
            handleClientISClosing();
                  
            Pane root = new Pane();

            root.getChildren().add(Manager.login);
            root.getChildren().add(Manager.signup);

            Manager.viewPane(Manager.login);

            //Manager.viewPane(Manager.login);
            Scene scene = new Scene(root);

            Window window = btn_onlineLogout.getScene().getWindow();
            Stage stage = (Stage) btn_onlineLogout.getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();

    }
    
    //*************************************************************************************//
    // get Player History
    //****************************************************************************************//
    @FXML
    private void handleBtnSeeHistory(ActionEvent event) {
        try {
            
            closeConnections();
            Parent root = FXMLLoader.load(getClass().getResource("/online/OnlineHistory.fxml"));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(OnlineMainPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    //**************************************************************************//
    // initilize connection to the server
    //********************************************************************// 
    private void initilization() {

        observableList.removeAll(lis);
        lis.clear();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "INITIALIZATION");

        String jsonText = JSONValue.toJSONString(jsonObject);

        ps.println(jsonText);
        ps.flush();
        System.out.println("init users");

    }
    
    
    //**************************************************************************//
    // Client logout
    //********************************************************************// 
    private void handleClientISClosing() {

        // notify server 
        // client try to logout
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "CLIENT_IS_CLOSING");

        jsonObject.put("EMAIL", ClientData.getMyEmail());

        String jsonText = JSONValue.toJSONString(jsonObject);
        ps.println(jsonText);
        ps.flush();
        System.out.println("send closing");
        closeConnections();
    }

    //**************************************************************************//
    // send play request
    //********************************************************************// 
    @FXML
    private void handleSelectedPlayer(MouseEvent event) {

        int selected = playerList.getSelectionModel().getSelectedIndex();

        ClientData.setOtherEmail(lis.get(selected).getEmail());

        // handle player in a match
        if (lis.get(selected).getStatus().equals("Playing Now")) {
            handleplayerIsPlayingNow(ClientData.getOtherEmail());

        } else {
            sendInvitationPopUp(ClientData.getOtherEmail());
            System.out.println("other email" + ClientData.getOtherEmail());
            System.out.println("handleSelectedPlayer");

        }

    }
    

    //**************************************************************************//
    // Selected Player is playing with other
    //********************************************************************// 
    private void handleplayerIsPlayingNow(String email) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
                showWinner.setTitle("Information");
                showWinner.setHeaderText(null);
                showWinner.setContentText(email + " is palying now with another player");
                showWinner.initModality(Modality.APPLICATION_MODAL);

                ButtonType btnSend = new ButtonType("ok");

                showWinner.getButtonTypes().setAll(btnSend);
                showWinner.showAndWait();
            }
        });

    }
    

    //**************************************************************************//
    // Alert to confirm send invitation
    //********************************************************************// 
    private void sendInvitationPopUp(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
                showWinner.setTitle("Invite Friend");
                showWinner.setHeaderText(null);
                showWinner.setContentText("Send invitation to " + message);
                showWinner.initModality(Modality.APPLICATION_MODAL);

                ButtonType btnSend = new ButtonType("Send");

                showWinner.getButtonTypes().setAll(btnSend);

                Optional<ButtonType> choices = showWinner.showAndWait();
                if (choices.get() == btnSend) {
                    //   if click ok
                    sendPlayRequest();
                    //initilization();
                }
            }
        });

    }

    //**************************************************************************//
    // send play request to the server
    //********************************************************************// 
    private void sendPlayRequest() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "SEND_PLAY_INVITATION");
        jsonObject.put("EMAIL", ClientData.getMyEmail());
        jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());

        String jsonText = JSONValue.toJSONString(jsonObject);
        ps.println(jsonText);
        ps.flush();
        System.out.println("send request to server" + jsonText);

    }

    //*************************************************************************************//
    // recieve play request from the server
    //****************************************************************************************//
    private void recievePlayRequest(String inComingjsonText) {

        // myFlagInvitation=false;
        try {
            Object obj = new JSONParser().parse(inComingjsonText);
            JSONObject jo2 = (JSONObject) obj;
            String email = (String) jo2.get("EMAIL");
            String emailTarget = (String) jo2.get("EMAIL_TARGET");

            if (emailTarget.equals(ClientData.getMyEmail())) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
                        showWinner.setTitle("Play Invitation");
                        showWinner.setHeaderText(null);
                        showWinner.setContentText(email + " Want to play with you");
                        showWinner.initModality(Modality.APPLICATION_MODAL);

                        ButtonType btnAccept = new ButtonType("Accept");
                        ButtonType btnRefuse = new ButtonType("Refuse");
                        showWinner.getButtonTypes().setAll(btnAccept, btnRefuse);
                        Optional<ButtonType> choices = showWinner.showAndWait();
                        if (choices.get() == btnAccept) {
                            ClientData.setOtherEmail(email);
                            //ClientData.setMyEmail(emailTarget);
                            responeToPlayrequest(inComingjsonText, true);
                        } else if (choices.get() == btnRefuse) {

                            ClientData.setOtherEmail(email);
                            responeToPlayrequest(inComingjsonText, false);
                        }
                        // handle more than one second
                        // close x button of alert
                    }
                });

            }

        } catch (ParseException ex) {
            Logger.getLogger(OnlineMainPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    //**************************************************************************//
    // handle recieving invitation from other players
    // response to play requset
    //********************************************************************// 
    private void responeToPlayrequest(String inComingjsonText, boolean key) {

        try {
            Object obj = new JSONParser().parse(inComingjsonText);
            JSONObject jo2 = (JSONObject) obj;
            String email = (String) jo2.get("EMAIL");
            String emailTarget = (String) jo2.get("EMAIL_TARGET");

            if (emailTarget.equals(ClientData.getMyEmail())) {
                if (key == true) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "SEND_RESPONSE_PLAY_INVITATION");
                    jsonObject.put("EMAIL", ClientData.getMyEmail());
                    jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());
                    jsonObject.put("KEY", "OK");
                    String jsonText = JSONValue.toJSONString(jsonObject);
                    ps.println(jsonText);
                    ps.flush();
                    System.out.println("ok   before fire game  " + jsonText);
                    OnlineGamePaneController.playerX = false;
                    OnlineGamePaneController.playerO = true;
                    firetheGame(false);

                }
                if (key == false) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("TYPE", "SEND_RESPONSE_PLAY_INVITATION");
                    jsonObject.put("EMAIL", ClientData.getMyEmail());
                    jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());
                    jsonObject.put("KEY", "NO");
                    String jsonText = JSONValue.toJSONString(jsonObject);
                    ps.println(jsonText);
                    ps.flush();
                    System.out.println("No   before fire game  " + jsonText);

                }

            }

        } catch (ParseException ex) {
            Logger.getLogger(OnlineMainPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //******************************************************************************//
    //**********************************************************************************//
    
    //*************************************************************************************//
    // handle your Invitation response
    // your invitation is accepted or refused
    //****************************************************************************************//
    private void recieveResponseToPlayRequest(String inComingjsonText) {

        //myFlagResponse=false;
        try {
            Object obj = new JSONParser().parse(inComingjsonText);
            JSONObject jo2 = (JSONObject) obj;
            String email = (String) jo2.get("EMAIL");
            String emailTarget = (String) jo2.get("EMAIL_TARGET");
            String key = (String) jo2.get("KEY");

            if (emailTarget.equals(ClientData.getMyEmail())) {
                //System.out.println("key " + key);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        if (key.equals("OK")) {
                            String game_id = (String) jo2.get("GAME_ID");
                            ClientData.setOtherEmail(email);
                            ClientData.setGameId(game_id);

                            Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
                            showWinner.setTitle("Congratlations !!");
                            showWinner.setHeaderText(null);
                            showWinner.setContentText("Your Invitation is Accepted");
                            //showWinner.initModality(Modality.APPLICATION_MODAL);
                            ButtonType btnOk = new ButtonType("ok");

                            // open new screen
                            showWinner.getButtonTypes().setAll(btnOk);

                            Optional<ButtonType> choices = showWinner.showAndWait();
                            //if (choices.get() == btnOk) 
                            {

                                OnlineGamePaneController.playerX = true;
                                OnlineGamePaneController.playerO = false;
                                firetheGame(true);
                            }
                        }

                        if (key.equals("NO")) {

                            Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
                            showWinner.setTitle("Sorry !!");
                            showWinner.setHeaderText(null);
                            showWinner.setContentText("Your Invitation is refused");
                            showWinner.initModality(Modality.APPLICATION_MODAL);

                            ButtonType btnOk = new ButtonType("ok");

                            showWinner.getButtonTypes().setAll(btnOk);

                            Optional<ButtonType> choices = showWinner.showAndWait();
                            if (choices.get() == btnOk) {

                            }
                        }

                    }
                });

            }
        } catch (ParseException ex) {
            Logger.getLogger(OnlineMainPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //*******************************************************************************//

    
    //*************************************************************************************//
    // handle Incoming data from server
    //****************************************************************************************//
    private void handleIncomingMessage(String jsonText) {

        try {
            Object obj = new JSONParser().parse(jsonText);
            JSONObject jo2 = (JSONObject) obj;
            String type = (String) jo2.get("TYPE");
            System.out.println("type is " + type);

            switch (type) {
                case "RECEIVE_PLAY_INVITATION":
                    //someone ask to play with me
                    // handle the response
                    //   done
                    //if(myFlagInvitation==true)
                    recievePlayRequest(jsonText);
                    break;

                case "RECEIVE_RESPONSE_PLAY_INVITATION":
                    System.out.println(jsonText);

                    // i ask someone to play
                    // handle his response
                    //if(myFlagResponse==true)
                    recieveResponseToPlayRequest(jsonText);
                    break;

                case "SERVER_IS_CLOSING":
                    // handle closing the server
                    //server about to close
                    handleClosetheServer();
                    break;

                case "ONLINEPLAYERS":
                    System.out.println("enter case");
                    // recieve online players data
                    getOnlinePlayer(jsonText);
                    break;

            }
        } catch (ParseException ex) {
            Logger.getLogger(OnlineMainPaneController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    //*************************************************************************************//
    // handle Incoming data of online players
    //****************************************************************************************//
    private void getOnlinePlayer(String jsonText) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                observableList.removeAll(observableList);
                observableList.removeAll(lis);
                lis.clear();

                try {
                    Object obj = new JSONParser().parse(jsonText);
                    JSONObject jo2 = (JSONObject) obj;
                    String type = (String) jo2.get("TYPE");
                    JSONArray jSONObject = (JSONArray) jo2.get("PLAYERS");

                    //observableList.removeAll(lis);
                    for (int i = 0; i < jSONObject.size(); i++) {
                        JSONObject s = (JSONObject) jSONObject.get(i);

                        String name = (String) s.get("NAME");
                        String email = (String) s.get("EMAIL");
                        long score = (long) s.get("SCORE");
                        String status = (String) s.get("STATUS");
                        if (status.equals("0")) {
                            status = "Online";
                        } else if (status.equals("1")) {
                            status = "Playing Now";

                        }
                        if (!email.equals(ClientData.getMyEmail())) {
                            lis.add(new Player(name, email, score, status));
                            observableList.add(name + "\t\t\t \t Score  ->>\t " + score + "\t\t\t\t" + status);
                        }
                    }
                    playerList.setItems(observableList);

                } catch (ParseException ex) {
                    Logger.getLogger(OnlineMainPaneController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        

    }
    

    //*************************************************************************************//
    // handle Connection to the sever is lost
    //****************************************************************************************//
    private void handleClosetheServer() {
        Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
        showWinner.setTitle("OOPS !!!");
        showWinner.setHeaderText(null);
        showWinner.setContentText("Sorry! The Server is Down ");
        showWinner.initModality(Modality.APPLICATION_MODAL);

        ButtonType btnOk = new ButtonType("Ok");
        showWinner.showAndWait();
        closeConnections();

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
            Logger.getLogger(OnlineMainPaneController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        initializeConn();

        onlineMainPaneTitle.setText(ClientData.getMyEmail());

    }

    
    //*************************************************************************************//
    // handle initialize Connection to the server
    //****************************************************************************************//
    private void initializeConn() {

        t = new Thread() {
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
                        System.out.println("First" + replyMsg);
                        handleIncomingMessage(replyMsg);

                    }

                } catch (IOException ex) {

                    redirectToHomePage();
                    //Logger.getLogger(OnlineMainPaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        t.setDaemon(true);
        t.start();
    }
    

    //*************************************************************************************//
    // in case of server down 
    // redirect to Offline mode
    //****************************************************************************************//
    private void redirectToHomePage() {

        

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                closeConnections();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/localAi/HomePane.fxml"));
                    Scene scene = new Scene(root);

                    Stage stage = (Stage) playerList.getScene().getWindow();
                //                stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


                    stage.setScene(scene);
                    stage.resizableProperty().setValue(Boolean.FALSE);

                    stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(OnlineMainPaneController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    //*************************************************************************************//
    // Open the Game
    //****************************************************************************************//
    private void firetheGame(boolean x) {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
                closeConnections();

                System.out.println("Enter fireGame ");

                if (x == true) {
                    OnlineGamePaneController.playerX = true;
                    OnlineGamePaneController.playerO = false;

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/online/OnlineGamePane.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) playerList.getScene().getWindow();
                                    stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


                        stage.setScene(scene);
                        stage.resizableProperty().setValue(Boolean.FALSE);

                        stage.show();

                    } catch (IOException ex) {
                        Logger.getLogger(OnlineMainPaneController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (x == false) {
                    OnlineGamePaneController.playerX = false;
                    OnlineGamePaneController.playerO = true;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/online/OnlineGamePane.fxml"));
                        Scene scene = new Scene(root);

                        Stage stage = (Stage) playerList.getScene().getWindow();
                        //            stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


                        stage.setScene(scene);
                        stage.resizableProperty().setValue(Boolean.FALSE);

                        stage.show();

                    } catch (IOException ex) {
                        Logger.getLogger(OnlineMainPaneController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

    }

}
