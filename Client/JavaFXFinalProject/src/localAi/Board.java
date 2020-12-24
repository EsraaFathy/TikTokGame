package localAi;

import files.FileWriterController;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

/**
 *
 * @author Menna
 */
public class Board {
    
    protected String theWinnerName;
    protected String player1Seq = "";
    protected String player2Seq = ""; 
    protected String theFirstPlayer;
    protected Button[] boardButtons = new Button[3 * 3];
    protected boolean isGameEnds;

    protected boolean isFirstPlayerTurn = true;
    protected boolean isDraw = false;
    private MediaPlayer  winSound, lostSound,drawsound;
    
    
    //name all label when new game
    public void newGame(Label currentPlayerSymbol)
    {
        player1Seq = "";
        player2Seq = "";
        theWinnerName = "";
        theFirstPlayer="";
        startNewGame(currentPlayerSymbol);
    }
    //color button of success
    public void colorBackgroundWinnerButtons(Button b1, Button b2, Button b3) {
        b1.setStyle("-fx-border-color:black;-fx-background-color: DarkBlue;-fx-border-width:5;");
        b2.setStyle("-fx-border-color:black;-fx-background-color: DarkBlue;-fx-border-width:5;");
        b3.setStyle("-fx-border-color:black;-fx-background-color: DarkBlue;-fx-border-width:5;");
    }
    //start new game
    public void startNewGame(Label currentPlayerSymbol) {        
        theWinnerName = "";
        isGameEnds = false;
        isDraw = false;
        setCurrentPlayerSymbol(currentPlayerSymbol);
        //make buttons without color and set empty string to every ones
        for (Button boardButton : boardButtons) {
            boardButton.setText("");
            boardButton.setStyle("-fx-background-color: none; -fx-cursor: hand;");
        }   
    }
    //tell user which one his next x or o by label
    public void setCurrentPlayerSymbol(Label currentPlayerSymbol) {
        if (isFirstPlayerTurn == true) {
            currentPlayerSymbol.setText("X");
            currentPlayerSymbol.setTextFill(Color.YELLOW);
        } else {
            currentPlayerSymbol.setText("O");
            currentPlayerSymbol.setTextFill(Color.RED);
        }
    }
    //store records in file
    public void storeRecords(Label firstPlayerName,Label secondPlayerName ) {
       
        if (isFirstPlayerTurn) {
            theFirstPlayer = firstPlayerName.getText();
        } else {
            theFirstPlayer = secondPlayerName.getText();
        }

        FileWriterController.addTheRecordedGame(firstPlayerName.getText(),secondPlayerName.getText(),player1Seq,player2Seq,theWinnerName,theFirstPlayer);
        player1Seq = "";
        player2Seq = "";
        theWinnerName = "";
        theFirstPlayer="";
    }
    //get the winner name or draw
    public void getTheWinnerName() {
        
                 
        if (isDraw) {
            Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
            showWinner.setTitle("Game Results");
            showWinner.setHeaderText(null);
            showWinner.setContentText("DRAW!!");
            showWinner.initModality(Modality.APPLICATION_MODAL);
            ButtonType btnOk = new ButtonType("ok");
            showWinner.getButtonTypes().setAll(btnOk);
            Optional<ButtonType> choices = showWinner.showAndWait();
           

            
            if (choices.get() == btnOk) {
            }
        } else {
            Alert showWinner = new Alert(Alert.AlertType.INFORMATION);
            showWinner.setTitle("Game Results");
            showWinner.setHeaderText(null);
            showWinner.setContentText("The Winner Is " + theWinnerName);
            showWinner.initModality(Modality.APPLICATION_MODAL);
            ButtonType btnOk = new ButtonType("ok");
            showWinner.getButtonTypes().setAll(btnOk);
            Optional<ButtonType> choices = showWinner.showAndWait();
           
            
            if (choices.get() == btnOk) {
            }
        }
    }
    //check if any move available ... empty buttons
    public boolean anyMovesAvailable() {

        for (int i = 0; i < 9; i++) {
            if (boardButtons[i].getText().equals("")) {
                return true;
            }
        }
        return false;
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
                winSound.play();
                winSound.setVolume(4);
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
    //free values of all vriables
    public void freeVars()
    {
        player1Seq = "";
        player2Seq = "";
        theWinnerName = "";
        theFirstPlayer="";
        isFirstPlayerTurn = true;  
    }
    //set labels names
    public void setLabelsNames(Label firstPlayerName,Label secondPlayerName ,Label firstPlayerScore,Label secondPlayerScore)
    {
        firstPlayerName.setText(SharedController.firstPlayerName);
        secondPlayerName.setText(SharedController.secondPlayerName);
        firstPlayerScore.setText("0");
        secondPlayerScore.setText("0");     
    }
}
