/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online;

import Mixed.IpPaneController;
import files.FileWriterController;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import localAi.SharedController;
import prefrence.ClientData;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.plugin2.jvm.RemoteJVMLauncher;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */
public class OnlineGamePaneController implements Initializable {

    @FXML
    private Label player1name;

    @FXML
    private Label player2name;

    @FXML
    private Button btnPauseResume;

    @FXML
    private Label currentChar;

    @FXML
    private CheckBox checkBox_Record;

    @FXML
    private Button btnCancel;

    @FXML
    private GridPane boardPane;

    private static String theWinnerName = "";
    private String player1Seq = "";
    private String player2Seq = "";
    private String player2Seq2 = "";

    private Button[] boardButtons = new Button[3 * 3];

    private boolean isGameEnds;

    private String index = "";
    private String theWinner = "";

    // who send the invitation will be char x
    public static boolean playerX;
    public static boolean playerO;

    private boolean player1Allow = true;
    private boolean player2Allow = false;

    private boolean isDraw = false;

    // who accept the invitaion will be char o
    private boolean recieveNotificationRecord = false;
    private MediaPlayer drawsound, lostSound, clickSound, clickSound2;

    private Thread t;
    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;

    EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
        actionPerformed(e);
    };

    @FXML
    private void handlebackButton(ActionEvent event) {

        player1Seq = "";
        player2Seq = "";
        player2Seq2 = "";
        theWinner = "";
        theWinnerName = "";
        isGameEnds = false;
        isDraw = false;

        for (Button boardButton : boardButtons) {
            boardButton.setText("");
            boardButton.setStyle("-fx-background-color: none; -fx-cursor: hand;");
        }
        sendPlayCancel();
    }

    @FXML
    private void handlepauseResumeButton(ActionEvent event) {

        if (btnPauseResume.getText().equals("pause")) {
            sendPlayPause();

        } else if (btnPauseResume.getText().equals("resume")) {
            sendPlayResume();

        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initializeConn();
        createGameBoard();
        startNewGame();
        int index1 = 0, index2 = 0;
        index1 = ClientData.getMyEmail().indexOf("@");
        index2 = ClientData.getOtherEmail().indexOf("@");

        recieveNotificationRecord = false;
        checkBox_Record.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    if (new_val == true) {
                        checkBox_Record.setDisable(true);
                        sendNotifyRecord();
                    }
                });

        player1name.setText(ClientData.getMyEmail().substring(0, index1));
        player2name.setText(ClientData.getOtherEmail().substring(0, index2));

    }

    private void initializeConn() {

        t = new Thread() {
            @Override
            public void run() {

                try {
                    mySocket = new Socket(IpPaneController.iipp, IpPaneController.PORT_NUMBER);
                    dis = new DataInputStream(mySocket.getInputStream());
                    ps = new PrintStream(mySocket.getOutputStream());

                    while (true) {

                        String replyMsg;
                        replyMsg = dis.readLine();
                        System.out.println("First" + replyMsg);
                        handleIncomingMessage(replyMsg);
                    }

                } catch (IOException ex) {
                    //Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
                    redirectToHomePage();
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }

    private void createGameBoard() {

        int row = 0;
        int column = 0;

        for (int i = 0; i < boardButtons.length; i++) {

            boardButtons[i] = new Button();

            boardButtons[i].setPrefSize(165, 160);

            boardButtons[i].setFont(Font.font("Arial", FontWeight.BOLD, 50));

            boardPane.add(boardButtons[i], column, row);

            boardButtons[i].addEventHandler(ActionEvent.ACTION, e -> {
                actionPerformed(e);
            });

            column++;
            if (column == 3) {
                row++;
                column = 0;
            }
        }

    }

    //color button of success
    public void colorBackgroundWinnerButtons(Button b1, Button b2, Button b3) {
        b1.setStyle("-fx-border-color:black;-fx-background-color: DarkBlue;-fx-border-width:5;");
        b2.setStyle("-fx-border-color:black;-fx-background-color: DarkBlue;-fx-border-width:5;");
        b3.setStyle("-fx-border-color:black;-fx-background-color: DarkBlue;-fx-border-width:5;");
    }

    private void checkIfGameEnds() {

        String t00 = boardButtons[0].getText();
        String t01 = boardButtons[1].getText();
        String t02 = boardButtons[2].getText();
        String t10 = boardButtons[3].getText();
        String t11 = boardButtons[4].getText();
        String t12 = boardButtons[5].getText();
        String t20 = boardButtons[6].getText();
        String t21 = boardButtons[7].getText();
        String t22 = boardButtons[8].getText();

        lostSound = new MediaPlayer(new Media(getClass().getResource("/sound/fail.mp3").toExternalForm()));
        drawsound = new MediaPlayer(new Media(getClass().getResource("/sound/Drum.mp3").toExternalForm()));

        if (t00.equals(t01) && t00.equals(t02) && !t00.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[0], boardButtons[1], boardButtons[2]);
        }

        if (t10.equals(t11) && t10.equals(t12) && !t10.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[3], boardButtons[4], boardButtons[5]);
        }

        if (t20.equals(t21) && t20.equals(t22) && !t20.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[6], boardButtons[7], boardButtons[8]);
        }

        if (t00.equals(t10) && t00.equals(t20) && !t00.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[0], boardButtons[3], boardButtons[6]);
        }

        if (t01.equals(t11) && t01.equals(t21) && !t01.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[1], boardButtons[4], boardButtons[7]);
        }

        if (t02.equals(t12) && t02.equals(t22) && !t02.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[2], boardButtons[5], boardButtons[8]);
        }

        if (t00.equals(t11) && t00.equals(t22) && !t00.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[0], boardButtons[4], boardButtons[8]);
        }

        if (t02.equals(t11) && t02.equals(t20) && !t02.equals("")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[2], boardButtons[4], boardButtons[6]);
        }
        if (!anyMovesAvailable() && isGameEnds == false) {
            isGameEnds = true;
            isDraw = true;

        }
        if (isGameEnds == true) {
            //first player win
            if (!player1Allow && isDraw == false) {
                theWinnerName = "Win";
                theWinner = evaluateboard(boardButtons);
                getTheWinnerName();

            } //second player win
            else if (player1Allow && isDraw == false) {
                theWinnerName = "Lose";
                theWinner = evaluateboard(boardButtons);
                lostSound.play();
                lostSound.setVolume(4);

                getTheWinnerName();

            } else if (!player1Allow && isDraw == true) {
                theWinnerName = "DRAW!";
                theWinner = "Draw";
                drawsound.play();
                drawsound.setVolume(4);

                getTheWinnerName();

            } else if (player1Allow && isDraw == true) {
                theWinnerName = "DRAW!";
                theWinner = "Draw";
                drawsound.play();
                drawsound.setVolume(4);
                getTheWinnerName();

            }

        }
    }

    private void startNewGame() {
        theWinnerName = "";
        isGameEnds = false;
        //setCurrentPlayerSymbol();

        for (Button boardButton : boardButtons) {
            boardButton.setText("");
            boardButton.setStyle("-fx-background-color: none; -fx-cursor: hand;");
        }
    }

    private void getTheWinnerName() {
        if (isDraw) {

            if (!player1Allow) {

                sendGameResult();
            }
            Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
            showWinner.setTitle("Game Results");
            showWinner.setHeaderText(null);
            showWinner.setContentText("DRAW!!");
            showWinner.initModality(Modality.APPLICATION_MODAL);

            ButtonType btnOk = new ButtonType("ok");

            showWinner.getButtonTypes().setAll(btnOk);

            Optional<ButtonType> choices = showWinner.showAndWait();
            //if (choices.get() == btnOk) 
            {

                if (!player1Allow) {
                    backToOnlineScreen();

                }
                if (player1Allow) {
                    backToOnlineScreen();

                }
            }
        } else {

            if (!player1Allow) {
                sendGameResult();

            }
            Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
            showWinner.setTitle("Game Results");
            showWinner.setHeaderText(null);
            showWinner.setContentText("You " + theWinnerName);
            showWinner.initModality(Modality.APPLICATION_MODAL);

            ButtonType btnOk = new ButtonType("ok");

            showWinner.getButtonTypes().setAll(btnOk);

            Optional<ButtonType> choices = showWinner.showAndWait();
            //if (choices.get() == btnOk) 
            {
                if (!player1Allow) {
                    playVidioWinner();

                }
                if (player1Allow) {

                    backToOnlineScreen();

                }
            }
        }
    }

    //send player index to onother player to drawn automatically
    private void sendPlayIndex(String index) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "SEND_PLAY_INDEX");
        jsonObject.put("EMAIL", ClientData.getMyEmail());
        jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());
        jsonObject.put("GAME_ID", ClientData.getGameId());
        jsonObject.put("INDEX", index);

        String jsonText = JSONValue.toJSONString(jsonObject);
        System.out.println("befor on send " + jsonText);
        ps.println(jsonText);
        ps.flush();

        System.out.println("send play index to the server" + jsonText);
    }

    //send player index to onother player to drawn automatically
    private void recievePlayIndex(String inComingjsonText) {

        try {
            System.out.println("before parse ptint incoming json text " + inComingjsonText);

            Object obj = new JSONParser().parse(inComingjsonText);
            JSONObject jo2 = (JSONObject) obj;
            String emailTarget = (String) jo2.get("EMAIL_TARGET");
            String email = (String) jo2.get("EMAIL");
            String index = (String) jo2.get("INDEX");
            String game_id = (String) jo2.get("GAME_ID");

            System.out.println("after parse ptint incoming json text" + inComingjsonText);

            if (emailTarget.equals(ClientData.getMyEmail())) {

                ClientData.setMyEmail(emailTarget);
                ClientData.setOtherEmail(email);
                ClientData.setGameId(game_id);

                System.out.println("i am going to draw");
                draw(index);
            }
        } catch (ParseException ex) {
            Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //draw your symbol on your board
    private void actionPerformed(ActionEvent e) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                clickSound = new MediaPlayer(new Media(getClass().getResource("/sound/Click2.mp3").toExternalForm()));

                Button clickedButton = (Button) e.getSource();
                if (playerX == true && playerO == false && btnPauseResume.getText().equals("pause")) {

                    currentChar.setText("X");
                    clickSound.seek(javafx.util.Duration.ZERO);
                    clickSound.play();

                    if (player1Allow == true) {
                        if (isGameEnds == false && clickedButton.getText().equals("")) {
                            System.out.println("I clicked button");
                            clickedButton.setTextFill(Color.YELLOW);
                            clickedButton.setText("X");

                            player1Allow = false;

                            for (Button boardButton : boardButtons) {
                                boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
                            }

                            if (clickedButton == boardButtons[0]) {
                                index = "0";
                                player1Seq = player1Seq.concat("0");
                            }
                            if (clickedButton == boardButtons[1]) {
                                index = "1";
                                player1Seq = player1Seq.concat("1");
                            }
                            if (clickedButton == boardButtons[2]) {
                                index = "2";
                                player1Seq = player1Seq.concat("2");
                            }
                            if (clickedButton == boardButtons[3]) {
                                index = "3";
                                player1Seq = player1Seq.concat("3");
                            }
                            if (clickedButton == boardButtons[4]) {
                                index = "4";
                                player1Seq = player1Seq.concat("4");
                            }
                            if (clickedButton == boardButtons[5]) {
                                index = "5";
                                player1Seq = player1Seq.concat("5");
                            }
                            if (clickedButton == boardButtons[6]) {
                                index = "6";
                                player1Seq = player1Seq.concat("6");
                            }
                            if (clickedButton == boardButtons[7]) {
                                index = "7";
                                player1Seq = player1Seq.concat("7");
                            }
                            if (clickedButton == boardButtons[8]) {
                                index = "8";
                                player1Seq = player1Seq.concat("8");
                            }
                            System.out.println(index);
                            //send index to another player

                            sendPlayIndex(index);
                            System.out.println("player1Seq " + player1Seq);

                            checkIfGameEnds();

                        }
                    }
                    if (player1Allow == false) {

                        for (Button boardButton : boardButtons) {
                            boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
                        }
                    }

                }

                if (playerX == false && playerO == true && btnPauseResume.getText().equals("pause")) {

                    currentChar.setText("O");

                    if (player2Allow == true) {

                        if (isGameEnds == false && clickedButton.getText().equals("")) {

                            System.out.println("I clicked button");
                            clickedButton.setTextFill(Color.YELLOW);
                            clickedButton.setText("O");
                            clickSound.seek(javafx.util.Duration.ZERO);
                            clickSound.play();
                            player2Allow = false;

                            for (Button boardButton : boardButtons) {
                                boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
                            }

                            if (clickedButton == boardButtons[0]) {
                                index = "0";
                                player2Seq = player2Seq.concat("0");
                            }
                            if (clickedButton == boardButtons[1]) {
                                index = "1";
                                player2Seq = player2Seq.concat("1");
                            }
                            if (clickedButton == boardButtons[2]) {
                                index = "2";
                                player2Seq = player2Seq.concat("2");
                            }
                            if (clickedButton == boardButtons[3]) {
                                index = "3";
                                player2Seq = player2Seq.concat("3");
                            }
                            if (clickedButton == boardButtons[4]) {
                                index = "4";
                                player2Seq = player2Seq.concat("4");
                            }
                            if (clickedButton == boardButtons[5]) {
                                index = "5";
                                player2Seq = player2Seq.concat("5");
                            }
                            if (clickedButton == boardButtons[6]) {
                                index = "6";
                                player2Seq = player2Seq.concat("6");
                            }
                            if (clickedButton == boardButtons[7]) {
                                index = "7";
                                player2Seq = player2Seq.concat("7");
                            }
                            if (clickedButton == boardButtons[8]) {
                                index = "8";
                                player2Seq = player2Seq.concat("8");
                            }

                            System.out.println(index);
                            //send index to another player
                            sendPlayIndex(index);

                            System.out.println("player2Seq " + player2Seq);

                            checkIfGameEnds();

                        }

                    }
                    if (player2Allow == false) {
                        for (Button boardButton : boardButtons) {
                            boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
                        }

                    }

                }

            }
        });

    }

    //draw the symbol in the bord of other player
    private void draw(String index1) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                  clickSound2 = new MediaPlayer(new Media(getClass().getResource("/sound/Stap.mp3").toExternalForm()));


                System.out.println("entered draw");
                int indexInt = Integer.parseInt(index1);

                if (playerX == true && playerO == false) {

                    boardButtons[indexInt].setTextFill(Color.RED);
                    boardButtons[indexInt].setText("O");
                     clickSound2.seek(javafx.util.Duration.ZERO);
                    clickSound2.play();
                    
                    player2Seq2 = player2Seq2.concat(index1);

                    System.out.println("i drawed the symbol");
                    checkIfGameEnds();
                    player1Allow = true;

                }
                if (playerO == true && playerX == false) {
                    boardButtons[indexInt].setTextFill(Color.RED);
                    boardButtons[indexInt].setText("X");
                     clickSound2.seek(javafx.util.Duration.ZERO);
                    clickSound2.play();

                    System.out.println("i drawed the symbol");
                    checkIfGameEnds();
                    player2Allow = true;
                }

            }
        });

    }

    private void handleIncomingMessage(String jsonText) {

        try {
            Object obj = new JSONParser().parse(jsonText);
            JSONObject jo2 = (JSONObject) obj;
            String type = (String) jo2.get("TYPE");
            System.out.println("type is " + type);

            switch (type) {
                case "RECEIVE_PLAY_INDEX":
                    System.out.println("i recieved from server");

                    recievePlayIndex(jsonText);
                    break;

                case "RECEIVE_PLAY_PAUSE":
                    System.out.println(jsonText);

                    System.out.println("i recieved from server");

                    recievePlayPause(jsonText);
                    break;

                case "RECEIVE_PLAY_RESUME":

                    recievePlayResume(jsonText);
                    break;

                case "RECEIVE_GAME_CANCEL":
                    System.out.println("enter case");
                    recieveGameCancelled(jsonText);
                    break;

                case "SERVER_IS_CLOSING":
                    System.out.println("enter case");
                    //recieveServerIsClosing(jsonText);
                    break;

                case "CLIENT_DOWEN":
                    System.out.println("client down");
                    otherPlayerIsDown(jsonText);
                    break;

                case "RECIEVE_NOTIFY_RECORD":
                    if (recieveNotificationRecord == false) {

                        recieveNotificationRecord = true;
                        recieveNotifyRecord(jsonText);

                    }

                    break;

            }
        } catch (ParseException ex) {
            Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //check if any move available
    public boolean anyMovesAvailable() {

        for (int i = 0; i < 9; i++) {
            if (boardButtons[i].getText().equals("")) {
                return true;
            }
        }
        return false;
    }

    //play vedio to winner
    private void playVidioWinner() {
        try {
            closeConnections();
            Parent root = FXMLLoader.load(getClass().getResource("/video/VideoPlayer.fxml"));

            Scene scene = new Scene(root);
            Window window = boardPane.getScene().getWindow();
            Stage stage = (Stage) boardPane.getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendGameResult() {

        System.out.println("the first sssssssssssssssssssssssssssssssssssssssssssss");

        if (checkBox_Record.isSelected()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("TYPE", "SEND_Game_RESULT");
            jsonObject.put("EMAIL", ClientData.getMyEmail());
            jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());
            jsonObject.put("GAME_ID", ClientData.getGameId());
            jsonObject.put("THE_WINNER", theWinner);
            jsonObject.put("FIRST_SEQUENCE", player1Seq);
            jsonObject.put("SECOND_SEQUENCE", player2Seq2);
            jsonObject.put("PLAY_FIRST", ClientData.getMyEmail());
            jsonObject.put("ALLOW_RECORD", "YES");

            String jsonText = JSONValue.toJSONString(jsonObject);
            ps.println(jsonText);
            ps.flush();
            System.out.println("send gameresult to the server" + jsonText);

        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("TYPE", "SEND_Game_RESULT");
            jsonObject.put("EMAIL", ClientData.getMyEmail());
            jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());
            jsonObject.put("GAME_ID", ClientData.getGameId());
            jsonObject.put("THE_WINNER", theWinner);
            jsonObject.put("FIRST_SEQUENCE", player1Seq);
            jsonObject.put("SECOND_SEQUENCE", player2Seq2);
            jsonObject.put("PLAY_FIRST", ClientData.getMyEmail());
            jsonObject.put("ALLOW_RECORD", "NO");

            String jsonText = JSONValue.toJSONString(jsonObject);
            ps.println(jsonText);
            ps.flush();
            System.out.println("send gameresult to the server" + jsonText);

        }
    }

    private void sendPlayCancel() {

        if (checkBox_Record.isSelected()) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("TYPE", "SEND_PLAY_CANCEL");
            jsonObject.put("EMAIL", ClientData.getMyEmail());
            jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());
            jsonObject.put("GAME_ID", ClientData.getGameId());
            jsonObject.put("THE_WINNER", ClientData.getOtherEmail());
            jsonObject.put("FIRST_SEQUENCE", player1Seq);
            jsonObject.put("SECOND_SEQUENCE", player2Seq2);
            jsonObject.put("PLAY_FIRST", ClientData.getMyEmail());
            jsonObject.put("ALLOW_RECORD", "YES");
            String jsonText = JSONValue.toJSONString(jsonObject);
            ps.println(jsonText);
            ps.flush();
            System.out.println("send cancel the game to the server" + jsonText);
            backToOnlineScreen();

        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("TYPE", "SEND_PLAY_CANCEL");
            jsonObject.put("EMAIL", ClientData.getMyEmail());
            jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());
            jsonObject.put("GAME_ID", ClientData.getGameId());
            jsonObject.put("THE_WINNER", ClientData.getOtherEmail());
            jsonObject.put("FIRST_SEQUENCE", player1Seq);
            jsonObject.put("SECOND_SEQUENCE", player2Seq2);
            jsonObject.put("PLAY_FIRST", ClientData.getMyEmail());
            jsonObject.put("ALLOW_RECORD", "NO");
            String jsonText = JSONValue.toJSONString(jsonObject);
            ps.println(jsonText);
            ps.flush();
            System.out.println("send cancel the game to the server" + jsonText);
            backToOnlineScreen();
        }

    }

    private void recieveGameCancelled(String jsonText) {
        try {
            Object obj = new JSONParser().parse(jsonText);
            JSONObject jo2 = (JSONObject) obj;
            String emailTarget = (String) jo2.get("EMAIL_TARGET");
            System.out.println(emailTarget);

            if (emailTarget.equals(ClientData.getMyEmail())) {
                System.out.println(emailTarget);

                player1Seq = "";
                player2Seq = "";
                player2Seq2 = "";
                theWinner = "";
                theWinnerName = "";
                isGameEnds = false;
                isDraw = false;

                gameisCanceled();
            }

        } catch (ParseException ex) {
            Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gameisCanceled() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
                showWinner.setTitle("Sorry");
                showWinner.setHeaderText(null);
                showWinner.setContentText("The Other Player cancel the Game");
                showWinner.initModality(Modality.APPLICATION_MODAL);

                ButtonType btnOk = new ButtonType("ok");

                showWinner.getButtonTypes().setAll(btnOk);

                Optional<ButtonType> choices = showWinner.showAndWait();
                //if (choices.get() == btnOk) 
                {

                    backToOnlineScreen();
                }
            }
        });

    }

    private void backToOnlineScreen() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {

                    closeConnections();
                    Parent root = FXMLLoader.load(getClass().getResource("/online/OnlineMainPane.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) btnPauseResume.getScene().getWindow();
                    stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

                    stage.setScene(scene);
                    stage.resizableProperty().setValue(Boolean.FALSE);

                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void sendPlayPause() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "SEND_PLAY_PAUSE");
        jsonObject.put("EMAIL", ClientData.getMyEmail());
        jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());
        jsonObject.put("GAME_ID", ClientData.getGameId());

        String jsonText = JSONValue.toJSONString(jsonObject);
        ps.println(jsonText);
        ps.flush();
        System.out.println("send pause game to the server" + jsonText);

        btnPauseResume.setText("resume");

        for (Button boardButton : boardButtons) {
            boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
        }

    }

    private void recievePlayPause(String jsonText) {

        try {
            Object obj = new JSONParser().parse(jsonText);
            JSONObject jo2 = (JSONObject) obj;
            String emailTarget = (String) jo2.get("EMAIL_TARGET");

            if (emailTarget.equals(ClientData.getMyEmail())) {

                gameisPaused();
            }
        } catch (ParseException ex) {
            Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void gameisPaused() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
                showWinner.setTitle("Sorry");
                showWinner.setHeaderText(null);
                showWinner.setContentText("The Other Player Pause the Game");
                showWinner.initModality(Modality.APPLICATION_MODAL);

                btnPauseResume.setText("resume");

                ButtonType btnOk = new ButtonType("ok");

                showWinner.getButtonTypes().setAll(btnOk);

                Optional<ButtonType> choices = showWinner.showAndWait();

                for (Button boardButton : boardButtons) {
                    boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
                }

            }
        });

    }

    private void sendPlayResume() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "SEND_PLAY_RESUME");
        jsonObject.put("EMAIL", ClientData.getMyEmail());
        jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());
        jsonObject.put("GAME_ID", ClientData.getGameId());

        String jsonText = JSONValue.toJSONString(jsonObject);
        ps.println(jsonText);
        ps.flush();
        System.out.println("send resume game to the server" + jsonText);
        btnPauseResume.setText("pause");

        for (Button boardButton : boardButtons) {
            boardButton.addEventHandler(ActionEvent.ACTION, eventHandler);
        }

    }

    private void recievePlayResume(String jsonText) {

        try {
            Object obj = new JSONParser().parse(jsonText);
            JSONObject jo2 = (JSONObject) obj;
            String emailTarget = (String) jo2.get("EMAIL_TARGET");

            if (emailTarget.equals(ClientData.getMyEmail())) {

                gameisResumed();
            }
        } catch (ParseException ex) {
            Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendNotifyRecord() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "SEND_NOTIFY_RECORD");
        jsonObject.put("EMAIL", ClientData.getMyEmail());
        jsonObject.put("EMAIL_TARGET", ClientData.getOtherEmail());

        String jsonText = JSONValue.toJSONString(jsonObject);
        ps.println(jsonText);
        ps.flush();

    }

    private void recieveNotifyRecord(String jsonText) {

        try {
            Object obj = new JSONParser().parse(jsonText);
            JSONObject jo2 = (JSONObject) obj;
            String emailTarget = (String) jo2.get("EMAIL_TARGET");

            if (emailTarget.equals(ClientData.getMyEmail())) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        checkBox_Record.setSelected(true);
                        checkBox_Record.setDisable(true);
                        Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
                        showWinner.setTitle("Recieve Notification");
                        showWinner.setHeaderText(null);
                        showWinner.setContentText("The other player start recording the game");
                        showWinner.initModality(Modality.APPLICATION_MODAL);

                        ButtonType btnOk = new ButtonType("OK ");
                        showWinner.getButtonTypes().setAll(btnOk);
                        showWinner.getButtonTypes().setAll(btnOk);

                        Optional<ButtonType> choices = showWinner.showAndWait();
                    }
                });
            }
        } catch (ParseException ex) {
            Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gameisResumed() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
                showWinner.setTitle("Game Resumed");
                showWinner.setHeaderText(null);
                showWinner.setContentText("The Other Player resume the Game");
                showWinner.initModality(Modality.APPLICATION_MODAL);

                btnPauseResume.setText("pause");

                ButtonType btnOk = new ButtonType("ok");

                showWinner.getButtonTypes().setAll(btnOk);

                Optional<ButtonType> choices = showWinner.showAndWait();

                for (Button boardButton : boardButtons) {
                    boardButton.addEventHandler(ActionEvent.ACTION, eventHandler);
                }

            }
        });

    }

    private void redirectToHomePage() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                closeConnections();

                System.out.println("Connection was rest");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/localAi/HomePane.fxml"));
                    Scene scene = new Scene(root);
                    Window window = boardPane.getScene().getWindow();
                    Stage stage = (Stage) boardPane.getScene().getWindow();
                    stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

                    stage.setScene(scene);
                    stage.resizableProperty().setValue(Boolean.FALSE);

                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

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
            Logger.getLogger(OnlineGamePaneController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void otherPlayerIsDown(String jsonText) {
        try {
            Object obj = new JSONParser().parse(jsonText);
            JSONObject jo2 = (JSONObject) obj;
            String email = (String) jo2.get("EMAIL");

            if (email.equals(ClientData.getOtherEmail())) {

                backToOnlineScreen();

            }
        } catch (ParseException ex) {
            Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String evaluateboard(Button[] boardButtons) {
        String t00 = boardButtons[0].getText();
        String t01 = boardButtons[1].getText();
        String t02 = boardButtons[2].getText();
        String t10 = boardButtons[3].getText();
        String t11 = boardButtons[4].getText();
        String t12 = boardButtons[5].getText();
        String t20 = boardButtons[6].getText();
        String t21 = boardButtons[7].getText();
        String t22 = boardButtons[8].getText();
        String[][] temp = new String[][]{{t00, t01, t02}, {t10, t11, t12}, {t20, t21, t22}};
        String rowSum = "";
        String Xwin = "XXX";
        String Owin = "OOO";
        // Check rows for winner.
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                rowSum += temp[row][col];
            }
            if (rowSum.equals(Xwin)) {
                return ClientData.getMyEmail();
            } else if (rowSum.equals(Owin)) {
                return ClientData.getOtherEmail();
            }
            rowSum = "";
        }
        // Check columns for winner.
        rowSum = "";
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                rowSum += temp[row][col];
            }
            if (rowSum.equals(Xwin)) {
                return ClientData.getMyEmail();
            } else if (rowSum.equals(Owin)) {
                return ClientData.getOtherEmail();
            }
            rowSum = "";
        }
        // Check diagonals for winner.
        // Top-left to bottom-right diagonal.
        rowSum = "";
        for (int i = 0; i < 3; i++) {
            rowSum += temp[i][i];
        }
        if (rowSum.equals(Xwin)) {
            return ClientData.getMyEmail();
        } else if (rowSum.equals(Owin)) {
            return ClientData.getOtherEmail();
        }
        // Top-right to bottom-left diagonal.
        rowSum = "";
        rowSum = temp[0][2] + temp[1][1] + temp[2][0];

        if (rowSum.equals(Xwin)) {
            return ClientData.getMyEmail();
        } else if (rowSum.equals(Owin)) {
            return ClientData.getOtherEmail();
        }
        return "Draw";
    }

}
