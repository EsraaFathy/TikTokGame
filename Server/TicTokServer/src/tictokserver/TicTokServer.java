package tictokserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class TicTokServer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new StartServer1(stage);
        
        Scene scene = new Scene(root);
        stage.setTitle("Tic-Tok Server");
        stage.getIcons().add(new Image(getClass().getResource("server.png").toExternalForm()));
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.setScene(scene);
        stage.resizableProperty().setValue(Boolean.FALSE);

        stage.show();
        stage.show();
    }


    public static void main(String[] args) {

        
        launch(args);
    }
    
}
