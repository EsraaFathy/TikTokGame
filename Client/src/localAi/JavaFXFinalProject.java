/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localAi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Mohamed
 */


    //*************************************************************************************//
    //  start point 
    //****************************************************************************************//
public class JavaFXFinalProject extends Application {
    
    
    public static boolean isSplash=false;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomePane.fxml"));
        
        Scene scene = new Scene(root);
         stage.getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

        stage.setScene(scene);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
