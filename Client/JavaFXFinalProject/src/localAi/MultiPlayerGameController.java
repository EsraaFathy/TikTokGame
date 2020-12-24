package localAi;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * FXML SharedController class
 *
 * @author Menna
 */
public class MultiPlayerGameController extends Board implements Initializable {

    @FXML
    private Label firstPlayerName;
    @FXML
    private Label secondPlayerName;
    @FXML
    private Label firstPlayerScore;
    @FXML
    private Label secondPlayerScore;
    @FXML
    private Label currentPlayerSymbol;
    @FXML
    private GridPane boardPane;
    @FXML
    private Button back;
    @FXML
    private Button newGame;
    private MediaPlayer  winSound, lostSound,clickSound,clickSound2;
    
    EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
        actionPerformed(e);
    };
    
    //initialize board
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLabelsNames(firstPlayerName,secondPlayerName ,firstPlayerScore,secondPlayerScore);
        createGameBoard();
        startNewGame(currentPlayerSymbol);
         winSound = new MediaPlayer(new Media(getClass().getResource("/sound/win.wav").toExternalForm()));
         lostSound = new MediaPlayer(new Media(getClass().getResource("/sound/fail.mp3").toExternalForm()));
    }
    //create board buttons and but them in screen
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
    //action berformed in game
    //action on game click button firstplayer draw X 
    //second player click button secondplayer draw O
    //i save the sequance of every one if user want record .. send it to file
    private void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button) e.getSource();
        clickSound= new MediaPlayer(new Media(getClass().getResource("/sound/Click2.mp3").toExternalForm()));
        clickSound2= new MediaPlayer(new Media(getClass().getResource("/sound/Stap.mp3").toExternalForm()));
        if (isGameEnds == false && clickedButton.getText().equals("")) {
            if (isFirstPlayerTurn) {
                clickedButton.setTextFill(Color.YELLOW);
                clickedButton.setText("X");
                clickSound.seek(javafx.util.Duration.ZERO);
                clickSound.play();

                if (SharedController.allowRecord == true) {
                    if (clickedButton == boardButtons[0]) {
                        player1Seq = player1Seq.concat("0");
                    }
                    if (clickedButton == boardButtons[1]) {
                        player1Seq = player1Seq.concat("1");
                    }
                    if (clickedButton == boardButtons[2]) {
                        player1Seq = player1Seq.concat("2");
                    }
                    if (clickedButton == boardButtons[3]) {
                        player1Seq = player1Seq.concat("3");
                    }
                    if (clickedButton == boardButtons[4]) {
                        player1Seq = player1Seq.concat("4");
                    }
                    if (clickedButton == boardButtons[5]) {
                        player1Seq = player1Seq.concat("5");
                    }
                    if (clickedButton == boardButtons[6]) {
                        player1Seq = player1Seq.concat("6");
                    }
                    if (clickedButton == boardButtons[7]) {
                        player1Seq = player1Seq.concat("7");
                    }
                    if (clickedButton == boardButtons[8]) {
                        player1Seq = player1Seq.concat("8");
                    }

                    System.out.println("player1Seq " + player1Seq);
                }

            } else {
                clickedButton.setTextFill(Color.RED);
                clickedButton.setText("O");
                clickSound2.seek(javafx.util.Duration.ZERO);
                clickSound2.play();

                if (SharedController.allowRecord == true) {
                    if (clickedButton == boardButtons[0]) {
                        player2Seq = player2Seq.concat("0");
                    }
                    if (clickedButton == boardButtons[1]) {
                        player2Seq = player2Seq.concat("1");
                    }
                    if (clickedButton == boardButtons[2]) {
                        player2Seq = player2Seq.concat("2");
                    }
                    if (clickedButton == boardButtons[3]) {
                        player2Seq = player2Seq.concat("3");
                    }
                    if (clickedButton == boardButtons[4]) {
                        player2Seq = player2Seq.concat("4");
                    }
                    if (clickedButton == boardButtons[5]) {
                        player2Seq = player2Seq.concat("5");
                    }
                    if (clickedButton == boardButtons[6]) {
                        player2Seq = player2Seq.concat("6");
                    }
                    if (clickedButton == boardButtons[7]) {
                        player2Seq = player2Seq.concat("7");
                    }
                    if (clickedButton == boardButtons[8]) {
                        player2Seq = player2Seq.concat("8");
                    }

                    System.out.println("player2Seq " + player2Seq);

                }
            }

            checkIfGameEnds(firstPlayerName,secondPlayerName,firstPlayerScore,secondPlayerScore);
            setCurrentPlayerSymbol(currentPlayerSymbol);
            isFirstPlayerTurn = !isFirstPlayerTurn;
            setCurrentPlayerSymbol(currentPlayerSymbol);

        }
    }
    //handel back button
    @FXML
    private void handlBackAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MultiPlayer.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MultiPlayerGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //handel new game button to start new game
    @FXML
    private void handleNewGameAction(ActionEvent event) {
       freeVars();
       startNewGame(currentPlayerSymbol);
    }

}
