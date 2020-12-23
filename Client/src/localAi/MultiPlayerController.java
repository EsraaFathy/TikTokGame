/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localAi;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML SharedController class
 *
 * @author Mohamed
 */
public class MultiPlayerController implements Initializable {

    @FXML
    private Label playerXLabel,playerOLabel;
  
    @FXML
    private TextField firstPlayerName,secondPlayerName;

    @FXML
    private Button start;
    
    @FXML
    private Button back;
    @FXML
    private Button btn_history;
    
    @FXML
    private CheckBox checkBoxAllowRecord;
    
    //*************************************************************************************//
    //  handleStartAction
    //****************************************************************************************//
    @FXML
    private void handleStartAction(ActionEvent event) {
        
        
        
        try {
            // check allowing recording or not
            if(checkBoxAllowRecord.isSelected())
                SharedController.allowRecord=true;
            
            else
                
                SharedController.allowRecord=false;
            SharedController.firstPlayerName=firstPlayerName.getText();
            SharedController.secondPlayerName=secondPlayerName.getText();
            
            
            Parent root = FXMLLoader.load(getClass().getResource("MultiPlayerGame.fxml"));
            
            Scene scene = new Scene(root);
            
            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            
            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MultiPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    //*************************************************************************************//
    //  handle back 
    //****************************************************************************************//
    @FXML
    private void handleBackAction(ActionEvent event)  {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HomePane.fxml"));
            
            Scene scene = new Scene(root);
            
            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            
            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MultiPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    //*************************************************************************************//
    //  invoke history screen
    //****************************************************************************************//
    @FXML
    private void handleHistoryAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/history/HistoryPane.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


        stage.setScene(scene);
        stage.resizableProperty().setValue(Boolean.FALSE);

        stage.show();

    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    
    }    
    
}
