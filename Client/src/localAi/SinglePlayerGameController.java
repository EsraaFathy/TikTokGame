package localAi;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
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
public class SinglePlayerGameController extends Board implements Initializable {

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

    private Random random = new Random();
    private int randomNumber;
    String rand="";
    String stPoint="";
    private final int MAX_DEPTH = 6;
    private MediaPlayer  winSound, lostSound, clickSound,drawsound;


    EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
        actionPerformed(e);
    };
    // handel back button to return home page
    @FXML
    private void handlBackAction(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SinglePlayer.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SinglePlayerGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // handel newgame button to start new game again
    @FXML
    private void handleNewGameAction(ActionEvent event){
        startNewGame();
    }
    // put names of user in screen & and create game board
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLabelsNames(secondPlayerName, firstPlayerName, firstPlayerScore, secondPlayerScore);
        createGameBoard();
        startNewGame();
    }
    //when start the game .. computer will play first
    private void startNewGame() {
        startNewGame(currentPlayerSymbol);
        //set currentPlayerSymbol always o to player move
        //comuter is very fast didnt appear dont need to change it 
        currentPlayerSymbol.setText("O");

        // first player is always the computer
        //start with easy mode with random numbers
        if (SharedController.minMaxAlgorithm == false) {
            randomNumber = random.nextInt(9);
            rand = Integer.toString(randomNumber);
            if (boardButtons[randomNumber].getText().equals("")) {
                boardButtons[randomNumber].setTextFill(Color.YELLOW);
                boardButtons[randomNumber].setText("X");
                if (SharedController.allowRecord == true) {
                    player1Seq = player1Seq.concat(rand);
                }
            }
        }
        //start with hard mode with minimaxalgorithm
        if (SharedController.minMaxAlgorithm == true) {
            int point = getBestMove(boardButtons);
            boardButtons[point].setTextFill(Color.YELLOW);
            boardButtons[point].setText("X");
            stPoint = Integer.toString(point);

            if (SharedController.allowRecord == true) {
                player1Seq = player1Seq.concat(stPoint);
            }
        }
    }
    // great board initialize buttons and add them to the board
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
    public void checkIfGameEnds(Label firstPlayerName,Label secondPlayerName,Label firstPlayerScore,Label secondPlayerScore) {

        String t00 = boardButtons[0].getText();
        String t01 = boardButtons[1].getText();
        String t02 = boardButtons[2].getText();
        String t10 = boardButtons[3].getText();
        String t11 = boardButtons[4].getText();
        String t12 = boardButtons[5].getText();
        String t20 = boardButtons[6].getText();
        String t21 = boardButtons[7].getText();
        String t22 = boardButtons[8].getText();
         winSound = new MediaPlayer(new Media(getClass().getResource("/sound/win.wav").toExternalForm()));
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
            isFirstPlayerTurn = true;
        }
        if (isGameEnds == true) {
            if (isFirstPlayerTurn&& isDraw == false) {
                lostSound.play();
                lostSound.setVolume(4);
                firstPlayerScore.setText(Integer.valueOf(firstPlayerScore.getText()) + 1 + "");
                theWinnerName = firstPlayerName.getText();
                getTheWinnerName();              
            } else if (!isFirstPlayerTurn&& isDraw == false)
            {
                winSound.play();
                winSound.setVolume(4);
                secondPlayerScore.setText(Integer.valueOf(secondPlayerScore.getText()) + 1 + "");
                theWinnerName = secondPlayerName.getText();
                getTheWinnerName();
                
            }
            else if (isDraw == true)
            {
                drawsound.play();
                drawsound.setVolume(4);
                theWinnerName = "DRAW!";
                getTheWinnerName();
            }
            if (SharedController.allowRecord == true){
               storeRecords(firstPlayerName,secondPlayerName);
            }
        }
    }
    //action on game first computer X
    //second player click button and draw O
    //i save the sequance of every one if user want record .. send it to file
    private void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button) e.getSource();
        clickSound= new MediaPlayer(new Media(getClass().getResource("/sound/Click2.mp3").toExternalForm()));
        if (isGameEnds == false && clickedButton.getText().equals("")) {
            clickSound.seek(javafx.util.Duration.ZERO);
            clickSound.play();
            if (SharedController.minMaxAlgorithm == false) {
                isFirstPlayerTurn = false;
                clickedButton.setTextFill(Color.RED);
                clickedButton.setText("O");

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

                }
                checkIfGameEnds(firstPlayerName, secondPlayerName, firstPlayerScore, secondPlayerScore);

                for (Button boardButton : boardButtons) {
                    boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
                }
                if (isGameEnds == false) {
                    isFirstPlayerTurn = true;
                    for (;;) {
                        randomNumber = random.nextInt(9);
                        rand = Integer.toString(randomNumber);
                        if (boardButtons[randomNumber].getText().equals("")) {
                            boardButtons[randomNumber].setTextFill(Color.YELLOW);
                            boardButtons[randomNumber].setText("X");
                            if (SharedController.allowRecord == true) {
                                player1Seq = player1Seq.concat(rand);
                            }
                            break;
                        }
                    }
                    checkIfGameEnds(firstPlayerName, secondPlayerName, firstPlayerScore, secondPlayerScore);

                    for (Button boardButton : boardButtons) {
                        boardButton.addEventHandler(ActionEvent.ACTION, eventHandler);
                    }

                }
            }
            
            if (SharedController.minMaxAlgorithm == true) {
                System.out.println("minmaxalgorithm");
                isFirstPlayerTurn = false;
                clickedButton.setTextFill(Color.RED);
                clickedButton.setText("O");

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
                   
                }
                System.out.println("player2Seq " + player2Seq);

                checkIfGameEnds(firstPlayerName, secondPlayerName, firstPlayerScore, secondPlayerScore);
                if (isGameEnds == false) {
                    for (Button boardButton : boardButtons) {
                        boardButton.removeEventHandler(ActionEvent.ACTION, eventHandler);
                    }

                    isFirstPlayerTurn = true;

                    int point = getBestMove(boardButtons);
                    boardButtons[point].setTextFill(Color.YELLOW);
                    boardButtons[point].setText("X");
                    stPoint = Integer.toString(point);

                    if (SharedController.allowRecord == true) {
                        player1Seq = player1Seq.concat(stPoint);
                    }
                    checkIfGameEnds(firstPlayerName, secondPlayerName, firstPlayerScore, secondPlayerScore);

                    for (Button boardButton : boardButtons) {
                        boardButton.addEventHandler(ActionEvent.ACTION, eventHandler);
                    }
                }

            }

        }
    }
    //mini max algorithm
    public int minimax(Button[] boardButtons, int depth, boolean isMaximizing) {
        int boardVal = evaluateboard(boardButtons);
        if (Math.abs(boardVal) == 10 || depth == 0
                || !anyMovesAvailable()) {
            return boardVal;
        }

        if (isMaximizing) {
            int highestVal = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (boardButtons[i].getText().equals("")) {
                    boardButtons[i].setTextFill(Color.RED);
                    boardButtons[i].setText("X");
                    highestVal = Math.max(highestVal, minimax(boardButtons, depth - 1, false));
                    boardButtons[i].setText("");
                }
            }
            return highestVal;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (boardButtons[i].getText().equals("")) {
                    boardButtons[i].setTextFill(Color.BLUE);
                    boardButtons[i].setText("O");
                    lowestVal = Math.min(lowestVal, minimax(boardButtons, depth - 1, true));
                    boardButtons[i].setText("");

                }

            }
            return lowestVal;
        }

    }
    //mini max return bestmove
    public int getBestMove(Button[] boardButtons) {
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;

        for (int i = 0; i < 9; i++) {

            if (boardButtons[i].getText().equals("")) {
                boardButtons[i].setTextFill(Color.BLUE);
                boardButtons[i].setText("X");
                int moveValue = minimax(boardButtons, MAX_DEPTH, false);
                boardButtons[i].setText("");
                if (moveValue > bestValue) {
                    bestMove = i;
                    bestValue = moveValue;
                }
            }

        }
        return bestMove;
    }
    //evaluateboard to mini max ... when x will win ..win o win and when draw
    private int evaluateboard(Button[] boardButtons) {
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
                return 10;
            } else if (rowSum.equals(Owin)) {
                return -10;
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
                return 10;
            } else if (rowSum.equals(Owin)) {
                return -10;
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
            return 10;
        } else if (rowSum.equals(Owin)) {
            return -10;
        }
        // Top-right to bottom-left diagonal.
        rowSum = "";
        rowSum = temp[0][2] + temp[1][1] + temp[2][0];

        if (rowSum.equals(Xwin)) {
            return 10;
        } else if (rowSum.equals(Owin)) {
            return -10;
        }
        return 0;
    }

}
