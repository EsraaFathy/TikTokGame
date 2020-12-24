/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package video;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.Window;
import online.OnlineGamePaneController;

/**
 * FXML Controller class
 *
 * @author Mohamed
 * 
 */

    

public class VideoPlayerController implements Initializable {
    
    
    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;
    
    @FXML
    private Button backButton;

    private static final String VIDEO_URL = "abcd.mp4";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        System.out.println("hello");
        System.out.println(url.toString());
        System.out.println(this.getClass().getResource(VIDEO_URL).toExternalForm());

        mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource(VIDEO_URL).toExternalForm()));
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
    }   
    
    
    @FXML
    private void handleBackButton(ActionEvent event)
    {
        //
        bcakToOnlineScreen();
        
    }
        private void bcakToOnlineScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/online/OnlineMainPane.fxml"));
            Scene scene = new Scene(root);
            Window window = backButton.getScene().getWindow();
            Stage stage = (Stage) backButton.getScene().getWindow();
                        stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));


            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(OnlineGamePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
}
