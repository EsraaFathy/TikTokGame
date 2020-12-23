package tictokserver;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartServer1 extends BorderPane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;

    public StartServer1(Stage stage) {

        anchorPane = new AnchorPane();
        imageView = new ImageView();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(200.0);
        //anchorPane.setStyle("-fx-background-color: #f5f5d2;");
        anchorPane.setStyle("-fx-background-color: #e5d1c2;");

        imageView.setFitHeight(284.0);
        imageView.setFitWidth(282.0);
        imageView.setLayoutX(136.0);
        imageView.setLayoutY(70.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("play.png").toExternalForm()));

        setCenter(anchorPane);

        anchorPane.getChildren().add(imageView);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DataBaseHandling.getInstance();
                if (DataBaseHandling.connection != null) {
                    stage.close();
                    Parent root = new HomeServer(stage);
                    Scene scene = new Scene(root);
                    Stage stage1 = new Stage();
                    stage1.getIcons().add(new Image(getClass().getResource("server.png").toExternalForm()));
                    stage1.setTitle("Tic-Tok Server");
                    stage1.initStyle(StageStyle.UNDECORATED);
                    stage1.centerOnScreen();
                    stage1.setScene(scene);
                    stage1.resizableProperty().setValue(Boolean.FALSE);
                    stage1.show();
                }
            }

        });
    }
}
