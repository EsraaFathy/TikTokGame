package localAi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Menna
 */
public class HomePaneController implements Initializable {

    @FXML
    private Label FavoriteMode;
    @FXML
    private Pane StartPane;
    
   
    
    //*************************************************************************************//
    //  invoke multiplayer screen
    //****************************************************************************************//
    @FXML
    private void handlePlayLocalAction(ActionEvent event){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("MultiPlayer.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //handel button that choose Single mode ..send user to single pane 
    
    
    //*************************************************************************************//
    //  invoke Ai screen
    //****************************************************************************************//
    @FXML
    private void handlePlayAiAction(ActionEvent event){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("SinglePlayer.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //handel button that choose online mode ..send user to ippane
    
    
    //*************************************************************************************//
    //  invoke online screen
    //****************************************************************************************//
    @FXML
    private void handlePlayNetworkAction(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Mixed/IpPane.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //handel button exit from game
    @FXML
    private void handleExitAction(ActionEvent event) {       
        Platform.exit();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       if(!JavaFXFinalProject.isSplash)
        {
            loadSplashScreen();
        }
        
    }
     private void loadSplashScreen()
    {
        try {
            JavaFXFinalProject.isSplash =true;
            
            Pane pane=FXMLLoader.load(getClass().getResource(("/splash/splash2.fxml")));
            StartPane.getChildren().setAll(pane);
            FadeTransition fadeIn=new FadeTransition(javafx.util.Duration.seconds(4), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            
            FadeTransition fadeout =new FadeTransition(javafx.util.Duration.seconds(4), pane);
            fadeout.setFromValue(1);
            fadeout.setToValue(0);
            fadeout.setCycleCount(1);
            
            fadeIn.play();
            fadeIn.setOnFinished((e)-> {
                fadeout.play();
            
            });
            fadeIn.setOnFinished((e)-> {
                try {
                    Pane parentcontent =FXMLLoader.load(getClass().getResource(("HomePane.fxml")));
                    StartPane.getChildren().setAll(parentcontent);
                } catch (IOException ex) {
                    Logger.getLogger(HomePaneController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        } catch (IOException ex) {
            Logger.getLogger(HomePaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        
    }
}
