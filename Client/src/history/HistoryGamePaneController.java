/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package history;

import Models.RecordObject;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import localAi.SharedController;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */

    //*************************************************************************************//
    // replay the recorded game to the user
    //****************************************************************************************//
public class HistoryGamePaneController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private Label player1name;
    @FXML
    private Label currentPlayerSymbol;
    @FXML
    private Label player2name;
    @FXML
    private Label charX;
    @FXML
    private Label charO;
    @FXML
    private GridPane boardPaneRecord;
    @FXML
    private Button btnStart;

    private Button[] boardButtons = new Button[3 * 3];
    private boolean xturn = true;
    private boolean isGameEnds;
    private boolean isDraw = false;    
    private boolean first=false;
     private MediaPlayer   clickSound,clickSound2;

    /**
     * Initializes the controller class.
     */
    @Override
    
    //*************************************************************************************//
    //  initialize UI
    //****************************************************************************************//
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        charX.setText("X");
        charO.setText("O");
        player1name.setText(RecordObject.getSelectedRecord().getFirstName());
        player2name.setText(RecordObject.getSelectedRecord().getSecondName());
        createGameBoard();
        for (Button boardButton : boardButtons) {
            boardButton.setText(" ");
            boardButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        }
    }
    
    

    
    //*************************************************************************************//
    //  handle back button
    //****************************************************************************************//
    @FXML
    private void handleButtonBack(ActionEvent event) {
        
        // who invoke current screen
        String ParentPath;
             if(SharedController.isOfflineParent==true)
                 ParentPath="/localAi/HomePane.fxml";
             else
                ParentPath="/online/OnlineMainPane.fxml";
        try {
            Parent root = FXMLLoader.load(getClass().getResource(ParentPath));
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            
        } catch (IOException ex) {
            Logger.getLogger(HistoryGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //*************************************************************************************//
    //  start play the recorded game
    //****************************************************************************************//
    @FXML
    private void handleButtonStart(ActionEvent event) {

        System.out.println(RecordObject.getSelectedRecord().getFirstSeq());
        System.out.println(RecordObject.getSelectedRecord().getSeconSeq());

        /*painGame(RecordObject.getSelectedRecord().getFirstSeq()
                ,RecordObject.getSelectedRecord().getSeconSeq());*/
        if(first==false){
           
            painGame(RecordObject.getSelectedRecord().getFirstSeq()
                ,RecordObject.getSelectedRecord().getSeconSeq());
           first=true;
           btnStart.setText("Play Again");
       }
        else
        {
            for (Button boardButton : boardButtons) {
                boardButton.setText(" ");
                boardButton.setStyle("-fx-background-color:transparent;-fx-border-color:black ;-fx-cursor: hand;");
            }
            xturn = true;
            painGame(RecordObject.getSelectedRecord().getFirstSeq()
                ,RecordObject.getSelectedRecord().getSeconSeq());
            System.out.println("played record again");
                        
        }
    }
    
    //*************************************************************************************//
    //  to colorize the cell for the winner
    //****************************************************************************************//
    public void colorBackgroundWinnerButtons(Button b1, Button b2, Button b3) {
        b1.setStyle("-fx-border-color:black;-fx-background-color: DarkBlue;-fx-border-width:5;");
        b2.setStyle("-fx-border-color:black;-fx-background-color: DarkBlue;-fx-border-width:5;");
        b3.setStyle("-fx-border-color:black;-fx-background-color: DarkBlue;-fx-border-width:5;");
    }

    
    //*************************************************************************************//
    //  create empty board
    //****************************************************************************************//
    private void createGameBoard() {

        int row = 0;
        int column = 0;

        for (int i = 0; i < boardButtons.length; i++) {

            boardButtons[i] = new Button();

            boardButtons[i].setPrefSize(165, 160);

            boardButtons[i].setFont(Font.font("Arial", FontWeight.BOLD, 50));

            boardPaneRecord.add(boardButtons[i], column, row);

            column++;
            if (column == 3) {
                row++;
                column = 0;
            }
        }

    }
         
    //without spaces  one string
    private void paintGame(String seq) {
        new Thread() {
            int i;
            

            @Override
            public void run() {
                clickSound= new MediaPlayer(new Media(getClass().getResource("/sound/Click2.mp3").toExternalForm()));
                clickSound2= new MediaPlayer(new Media(getClass().getResource("/sound/Banana.mp3").toExternalForm()));

                int[] nums = new int[seq.length()];
                for (i = 0; i < seq.length(); i++) {
                    nums[i] = Character.getNumericValue(seq.charAt(i));

                }

                for (i = -1; i < nums.length-1; i++) {
                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HistoryGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    clickSound.seek(javafx.util.Duration.ZERO);
                    clickSound.play();
                    if (xturn == true) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                boardButtons[nums[i]].setTextFill(Color.YELLOW);
                                boardButtons[nums[i]].setText("X");
                                clickSound.seek(javafx.util.Duration.ZERO);
                                clickSound.play();
                                xturn = false;
                                checkIfGameEnds();
                                System.out.println(nums[i]);
                            }
                        });

                    } else {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                boardButtons[nums[i]].setTextFill(Color.RED);
                                boardButtons[nums[i]].setText("O");
                                clickSound2.seek(javafx.util.Duration.ZERO);
                                clickSound2.play();
                                xturn = true;
                                checkIfGameEnds();
                                System.out.println(nums[i]);
                            }
                        });

                    }
                }
            }
        }.start();

    }
    // without spaces 2 string
    
    //*************************************************************************************//
    //  get the firstseq   secondseq to draw on screen  
    //****************************************************************************************//
    private void painGame(String seq,String seq2)
    {
        
         new Thread() {
            int i,k;
            @Override
            
            public void run() {
                String both =seq+seq2;
                int[] nums1 = new int[seq.length()];
                int[] nums2 = new int[seq2.length()];
                int[] arrOfboth = new int[both.length()];
                clickSound= new MediaPlayer(new Media(getClass().getResource("/sound/Click2.mp3").toExternalForm()));
                clickSound2= new MediaPlayer(new Media(getClass().getResource("/sound/Stap.mp3").toExternalForm()));
                
                for (i = 0; i < seq.length(); i++) {
                    nums1[i] = Character.getNumericValue(seq.charAt(i));
                }
                for (i = 0; i < seq2.length(); i++) {
                    nums2[i] = Character.getNumericValue(seq2.charAt(i));
                }
                for (i = 0; i < both.length(); i++) {
                    arrOfboth[i] = Character.getNumericValue(both.charAt(i));
                }

                for ( i=-1, k=0; i<=arrOfboth.length; i++,k++){
                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HistoryGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                    if (xturn == true) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                for (int j=(int)i/2; j<nums1.length; j++) {
                                    System.out.println(nums1[j]);
                                    boardButtons[nums1[j]].setTextFill(Color.YELLOW);
                                    boardButtons[nums1[j]].setText("X");
                                    xturn = false;
                                     clickSound.seek(javafx.util.Duration.ZERO);
                                     clickSound.play();
                                    break;
                                } 
                            }    

                        });
                    }
                    else
                    {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {                        
                                for (int m=i-(int)(k/2); m<nums2.length; m++) {
                                    System.out.println(nums2[m]);
                                    boardButtons[nums2[m]].setTextFill(Color.RED);
                                    boardButtons[nums2[m]].setText("O");
                                    xturn = true;
                                    clickSound2.seek(javafx.util.Duration.ZERO);
                                    clickSound2.play();
                                    break;
                                }
                            }
                        });    
                    }

                    checkIfGameEnds();
                }    
            }
         }.start();
    }

    //*************************************************************************************//
    //  check if game ended or not
    //****************************************************************************************//
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

        if (t00.equals(t01) && t00.equals(t02) && !t00.equals(" ")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[0], boardButtons[1], boardButtons[2]);
        }

        if (t10.equals(t11) && t10.equals(t12) && !t10.equals(" ")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[3], boardButtons[4], boardButtons[5]);
        }

        if (t20.equals(t21) && t20.equals(t22) && !t20.equals(" ")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[6], boardButtons[7], boardButtons[8]);
        }

        if (t00.equals(t10) && t00.equals(t20) && !t00.equals(" ")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[0], boardButtons[3], boardButtons[6]);
        }

        if (t01.equals(t11) && t01.equals(t21) && !t01.equals(" ")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[1], boardButtons[4], boardButtons[7]);
        }

        if (t02.equals(t12) && t02.equals(t22) && !t02.equals(" ")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[2], boardButtons[5], boardButtons[8]);
        }

        if (t00.equals(t11) && t00.equals(t22) && !t00.equals(" ")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[0], boardButtons[4], boardButtons[8]);
        }

        if (t02.equals(t11) && t02.equals(t20) && !t02.equals(" ")) {
            isGameEnds = true;
            colorBackgroundWinnerButtons(boardButtons[2], boardButtons[4], boardButtons[6]);
        }
        if (!anyMovesAvailable() && isGameEnds == false) {
            isGameEnds = true;
            isDraw = true;
        }

    }

    //*************************************************************************************//
    //  check if any cell available for playing
    //****************************************************************************************//
    public boolean anyMovesAvailable() {

        for (int i = 0; i < 9; i++) {
            if (boardButtons[i].getText().equals("")) {
                return true;
            }
        }
        return false;
    }

}
