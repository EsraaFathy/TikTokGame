/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package history;

import Models.RecordObject;
import Models.Player;
import Models.Records;
import files.FileWriterController;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import online.OnlineMainPaneController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author Mohamed
 */

    //*************************************************************************************//
    //  load the recorded games from a file 
    //  use observable listview  
    //****************************************************************************************//
public class HistoyPaneController implements Initializable {

    private ArrayList<Records> recieved = new ArrayList<>();

    ObservableList observableList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> historyList;
    @FXML
    private Button btnBack;

    ObservableList list = FXCollections.observableArrayList();

    //*************************************************************************************//
    //  handle back button
    //****************************************************************************************//
    @FXML
    private void handleBackbutton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/localAi/HomePane.fxml"));

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

    //*************************************************************************************//
    //  handle handleSelectedRecord 
    //****************************************************************************************//
    @FXML
    private void handleSelectedRecord(MouseEvent event) {
        int selected = historyList.getSelectionModel().getSelectedIndex();

        String firstName = recieved.get(selected).getFirstName();
        String secondName = recieved.get(selected).getSecondName();
        String firstSequence = recieved.get(selected).getFirstSeq();
        String secondSequence = recieved.get(selected).getSeconSeq();
        String theWinner = recieved.get(selected).getTheWinner();
        Records recordHistory = new Records(firstName, secondName, firstSequence, secondSequence, theWinner, theWinner);
        
        RecordObject.setSelectedRecord(recordHistory);

        playRecordingGame(recordHistory);
    }

    
    
    //*************************************************************************************//
    //  handle playRecordingGame 
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
    //  invoke game screen
    //****************************************************************************************//
    private void openPaintPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/history/HistoryGamePane.fxml"));
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnBack.getScene().getWindow();
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

        //
        updateUi();
    }

    
    
    //*************************************************************************************//
    //  open the file 
    // get the data
    // store in listview
    // show the data 
    //****************************************************************************************//
    private void updateUi() {

        recieved = FileWriterController.getAllRecords();

        observableList.removeAll(observableList);
        
        for (int i = 0; i < recieved.size(); i++) {

            String firstName = recieved.get(i).getFirstName();
            String secondName = recieved.get(i).getSecondName();
            String firstsequence = recieved.get(i).getFirstSeq();
            String secondsequence = recieved.get(i).getSeconSeq();
            String theWinner = recieved.get(i).getTheWinner();
            String firstPlay = recieved.get(i).getPlayFirst();

            //lis.add(new Player(name, email,score));
            observableList.add(firstName + "\t with\t\t" + secondName + "\tthe winner -> \t\t" + theWinner 
            +"\t\t\t" + firstsequence+ " \t\t\t" + secondsequence+ "\t\t");
        }        
        historyList.setItems(observableList);
        //historyList.getItems().addAll(observableList);

    }
}
