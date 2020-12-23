package tictokserver;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HomeServer extends BorderPane {

    protected static double onlineNumber = 0, offLineNumber = 0;
    protected final AnchorPane anchorPane;
    protected final FlowPane flowPane;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final ImageView stopButton;
    protected final ImageView newPlayerButton;
    protected final FlowPane flowPane0;
    protected final FlowPane flowPane1;
    protected static TextArea onlineText;
    protected static TextArea offlineText;
    protected static TextArea ongameText;
    protected final Label totalLablel;
    protected PieChart pieChart;
    protected final Label ip;

    protected ObservableList<PieChart.Data> pieChartData;
    static ArrayList<String> arrOnline = new ArrayList<>();
    static ArrayList<String> arrOffline = new ArrayList<>();
    static ArrayList<String> arrOnGaming = new ArrayList<>();

    static private ListView<String> onlinwListView;
    static ObservableList onLineObservableList = FXCollections.observableArrayList();

    static private ListView<String> offlinwListView;
    static ObservableList offlineObservableList = FXCollections.observableArrayList();

    static private ListView<String> onGameListView;
    static ObservableList onGameObservableList = FXCollections.observableArrayList();

    String serverIp;
    static int totalNumberOfGaming = 0;

    public HomeServer(Stage stage) {

        //create object for start server
        ServerNetwork.getInstance();
        DataBaseHandling.getInstance().stopServerOfflineAll();

        anchorPane = new AnchorPane();
        flowPane = new FlowPane();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        stopButton = new ImageView();
        newPlayerButton = new ImageView();
        flowPane0 = new FlowPane();
        flowPane1 = new FlowPane();
        onlineText = new TextArea();
        offlineText = new TextArea();
        ongameText = new TextArea();
        pieChart = new PieChart();
        totalLablel = new Label();
        ip = new Label();
        onlinwListView = new ListView<String>();
        offlinwListView = new ListView<String>();
        onGameListView = new ListView<String>();
        anchorPane.setStyle("-fx-background-color: #e5d1c2;");
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(812.0);
        setPrefWidth(800.0);

        totalLablel.setLayoutX(80.0);
        totalLablel.setLayoutY(448.0);
        totalLablel.setPrefHeight(57.0);
        totalLablel.setPrefWidth(375.0);
        totalLablel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        totalLablel.setFont(new Font("Arial Black", 16.0));
        setCenter(anchorPane);

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(300.0);

        BorderPane.setAlignment(ip, javafx.geometry.Pos.CENTER);
        ip.setPrefHeight(40.0);
        ip.setPrefWidth(240.0);
        ip.setText("server IP is " + getIP());
        ip.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        ip.setFont(new Font("Arial Black", 16.0));
        BorderPane.setMargin(ip, new Insets(0.0));
        setTop(ip);

        flowPane.setLayoutX(29.0);
        flowPane.setLayoutY(14.0);
        flowPane.setPrefHeight(51.0);
        flowPane.setPrefWidth(729.0);

        label.setPrefHeight(56.0);
        label.setPrefWidth(234.0);
        label.setText("              Online");
        label.setTextFill(Color.GREEN);
        label.setFont(new Font("Arial Black", 16.0));
        label.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);
        label.setWrapText(true);

        label0.setPrefHeight(53.0);
        label0.setPrefWidth(221.0);
        label0.setText("                   Offline");
        label0.setFont(new Font("Arial Black", 16.0));
        label0.setTextFill(Color.RED);

        label1.setPrefHeight(54.0);
        label1.setPrefWidth(252.0);
        label1.setText("                      On game");
        label1.setFont(new Font("Arial Black", 16.0));
        label1.setTextFill(Color.BLUE);

        stopButton.setFitHeight(150.0);
        stopButton.setFitWidth(200.0);
        stopButton.setLayoutX(603.0);
        stopButton.setLayoutY(478.0);
        stopButton.setPickOnBounds(true);
        stopButton.setPreserveRatio(true);
        stopButton.setImage(new Image(getClass().getResource("stop.png").toExternalForm()));

        newPlayerButton.setFitHeight(150.0);
        newPlayerButton.setFitWidth(200.0);
        newPlayerButton.setLayoutX(603.0);
        newPlayerButton.setLayoutY(478.0);
        newPlayerButton.setPickOnBounds(true);
        newPlayerButton.setPreserveRatio(true);
        newPlayerButton.setImage(new Image(getClass().getResource("addplayer.png").toExternalForm()));

        flowPane1.setLayoutX(0.0);
        flowPane1.setLayoutY(500.0);
        flowPane1.setPrefHeight(300.0);
        flowPane1.setPrefWidth(800.0);
        flowPane1.setPadding(new Insets(0.0, 0.0, 0.0, 50.0));

        flowPane0.setLayoutX(26.0);
        flowPane0.setLayoutY(85.0);
        flowPane0.setPrefHeight(359.0);
        flowPane0.setPrefWidth(729.0);
        flowPane0.setPadding(new Insets(0.0, 0.0, 0.0, 50.0));

        onlinwListView.setPrefHeight(30.0);
        onlinwListView.setPrefWidth(200.0);
        onlinwListView.setStyle("-fx-text-fill: green");
        FlowPane.setMargin(onlinwListView, new Insets(10.0));

        offlinwListView.setPrefHeight(330.0);
        offlinwListView.setPrefWidth(200.0);
        offlinwListView.setStyle("-fx-text-fill: green");
        FlowPane.setMargin(offlinwListView, new Insets(10.0));

        onGameListView.setPrefHeight(330.0);
        onGameListView.setPrefWidth(200.0);
        onGameListView.setStyle("-fx-text-fill: blue");
        FlowPane.setMargin(onGameListView, new Insets(10.0));

        ArrayList<String> arr = new ArrayList<>();
        arr = DataBaseHandling.getInstance().getOnLine();
        for (String c : arr) {
            onlineNumber++;
        }
        ArrayList<String> arr1 = new ArrayList<>();
        arr1 = DataBaseHandling.getInstance().getOffLine();
        for (String c : arr1) {
            offLineNumber++;
        }
        pieChart.setLayoutX(603.0);
        pieChart.setLayoutY(478.0);
        pieChart.setPrefHeight(250.0);
        pieChart.setPrefWidth(300.0);
        pieChart.setStartAngle(270);
        // pieChart.setStyle("");
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("OnLine \n" + (int) onlineNumber, onlineNumber),
                new PieChart.Data("OffLine \n" + (int) offLineNumber, offLineNumber));
        pieChart.setData(pieChartData);

        pieChartData.get(0).getNode().setStyle("-fx-pie-color: green");
        pieChartData.get(1).getNode().setStyle("-fx-pie-color: red");
        pieChart.setLabelsVisible(true);

        ScrollPane scrollPaneOnline = new ScrollPane();
        scrollPaneOnline.setContent(onlineText);
        scrollPaneOnline.setFitToWidth(true);
        scrollPaneOnline.setFitToHeight(true);
        ScrollPane scrollPaneOffline = new ScrollPane();
        scrollPaneOffline.setContent(offlineText);
        scrollPaneOffline.setFitToWidth(true);
        scrollPaneOffline.setFitToHeight(true);

        ScrollPane scrollPaneOnGame = new ScrollPane();
        scrollPaneOnGame.setContent(ongameText);
        scrollPaneOnGame.setFitToWidth(true);
        scrollPaneOnGame.setFitToHeight(true);

        setCenter(anchorPane);

        flowPane.getChildren().add(label);
        flowPane.getChildren().add(label0);
        flowPane.getChildren().add(label1);
        anchorPane.getChildren().add(flowPane);
        flowPane1.getChildren().add(pieChart);

        flowPane1.getChildren().add(newPlayerButton);
        flowPane1.getChildren().add(stopButton);
        anchorPane.getChildren().add(flowPane1);
        flowPane0.getChildren().add(onlinwListView);
        flowPane0.getChildren().add(offlinwListView);
        flowPane0.getChildren().add(onGameListView);
        anchorPane.getChildren().add(totalLablel);

        anchorPane.getChildren().add(flowPane0);

        Thread h = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    Platform.runLater(() -> setOnlineText());
                    Platform.runLater(() -> setOffText());
                    Platform.runLater(() -> setOnGamingText());
                    Platform.runLater(() -> pieChartData = FXCollections.observableArrayList(
                            new PieChart.Data("OnLine \n" + (int) onlineNumber, onlineNumber),
                            new PieChart.Data("OffLine \n" + (int) offLineNumber, offLineNumber)));
                    Platform.runLater(() -> pieChart.setData(pieChartData));
                    Platform.runLater(() -> setTotalNumberOfGamesPlayed());

                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HomeServer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        h.start();

        stopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DataBaseHandling.getInstance().stopServerOfflineAll();
                DataBaseHandling.getInstance().clossingDataBase();
                ServerNetwork.getInstance().closeNetwork();

                System.exit(0);
            }
        });
        newPlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage s = new Stage();

                Parent root = new RecordPlayer(s);

                Scene scene = new Scene(root);
                s.setTitle("Record New Player");
                s.getIcons().add(new Image(getClass().getResource("server.png").toExternalForm()));
                s.setScene(scene);
                s.centerOnScreen();
                s.setScene(scene);
                s.resizableProperty().setValue(Boolean.FALSE);
                s.show();
                s.show();
            }
        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                DataBaseHandling.getInstance().stopServerOfflineAll();
                DataBaseHandling.getInstance().clossingDataBase();
                ServerNetwork.getInstance().closeNetwork();
                System.out.println("print....");
                Platform.exit();
                System.exit(0);
            }
        });

    }

    public static void setOnlineText() {

        onLineObservableList.removeAll(arrOnline);
        arrOnline.clear();
        arrOnline = DataBaseHandling.getInstance().getOnLine();
        onlineNumber = (double) arrOnline.size();
        onLineObservableList.addAll(arrOnline);
        onlinwListView.setItems(onLineObservableList);

    }

    public static void setOffText() {

        offlineObservableList.removeAll(arrOffline);
        arrOffline.clear();
        arrOffline = DataBaseHandling.getInstance().getOffLine();
        offLineNumber = (double) arrOffline.size();
        offlineObservableList.addAll(arrOffline);
        offlinwListView.setItems(offlineObservableList);
    }

    public static void setOnGamingText() {
        onGameObservableList.removeAll(arrOnGaming);
        arrOnGaming.clear();
        arrOnGaming = DataBaseHandling.getInstance().getOnGaming();
        onGameObservableList.addAll(arrOnGaming);
        onGameListView.setItems(onGameObservableList);

    }

    public void setTotalNumberOfGamesPlayed() {
        totalNumberOfGaming = DataBaseHandling.getInstance().returnTotalNumberOfGames();
        totalLablel.setText("Total Number Of Games Played = " + totalNumberOfGaming);

    }

    public String getIP() {
        InetAddress ip = null;
        String arr[] = {};
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            String s = String.valueOf(ip);
            arr = s.split("/");
        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        return arr[1];

    }

}
