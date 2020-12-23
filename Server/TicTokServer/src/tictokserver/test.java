package tictokserver;

import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

public abstract class test extends BorderPane {

    protected final AnchorPane anchorPane;
    protected final FlowPane flowPane;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final ImageView stopButton;
    protected final FlowPane flowPane0;
    protected final TextArea onlineText;
    protected final TextArea offlineText;
    protected final TextArea ongameText;
    protected final PieChart pieChart;
    protected final Label totalLablel;
    protected final Label ip;

    public test() {

        anchorPane = new AnchorPane();
        flowPane = new FlowPane();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        stopButton = new ImageView();
        flowPane0 = new FlowPane();
        onlineText = new TextArea();
        offlineText = new TextArea();
        ongameText = new TextArea();
        pieChart = new PieChart();
        totalLablel = new Label();
        ip = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(812.0);
        setPrefWidth(800.0);
        setStyle("-fx-background-color: #3656;");

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(746.0);
        anchorPane.setPrefWidth(800.0);

        flowPane.setLayoutX(29.0);
        flowPane.setLayoutY(14.0);
        flowPane.setPrefHeight(51.0);
        flowPane.setPrefWidth(729.0);

        label.setPrefHeight(56.0);
        label.setPrefWidth(234.0);
        label.setText("              Online");
        label.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);
        label.setWrapText(true);

        label0.setPrefHeight(53.0);
        label0.setPrefWidth(221.0);
        label0.setText("                   Offline");

        label1.setPrefHeight(54.0);
        label1.setPrefWidth(252.0);
        label1.setText("                      On game");

        stopButton.setFitHeight(150.0);
        stopButton.setFitWidth(200.0);
        stopButton.setLayoutX(605.0);
        stopButton.setLayoutY(580.0);
        stopButton.setPickOnBounds(true);
        stopButton.setPreserveRatio(true);
        stopButton.setImage(new Image(getClass().getResource("stop.png").toExternalForm()));

        flowPane0.setLayoutX(26.0);
        flowPane0.setLayoutY(85.0);
        flowPane0.setPrefHeight(359.0);
        flowPane0.setPrefWidth(729.0);

        onlineText.setEditable(false);
        onlineText.setPrefHeight(330.0);
        onlineText.setPrefWidth(220.0);
        FlowPane.setMargin(onlineText, new Insets(10.0));

        offlineText.setEditable(false);
        offlineText.setPrefHeight(330.0);
        offlineText.setPrefWidth(220.0);
        FlowPane.setMargin(offlineText, new Insets(10.0));

        ongameText.setEditable(false);
        ongameText.setPrefHeight(330.0);
        ongameText.setPrefWidth(220.0);
        FlowPane.setMargin(ongameText, new Insets(10.0));

        pieChart.setLayoutX(33.0);
        pieChart.setLayoutY(528.0);
        pieChart.setPrefHeight(213.0);
        pieChart.setPrefWidth(367.0);

        totalLablel.setLayoutX(175.0);
        totalLablel.setLayoutY(448.0);
        totalLablel.setPrefHeight(57.0);
        totalLablel.setPrefWidth(375.0);
        totalLablel.setText("Total Number Of Games Playing =");
        totalLablel.setFont(new Font(19.0));
        setCenter(anchorPane);

        BorderPane.setAlignment(ip, javafx.geometry.Pos.CENTER);
        ip.setPrefHeight(40.0);
        ip.setPrefWidth(131.0);
        ip.setText("192.128.1.3");
        ip.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        ip.setFont(new Font("Arial Black", 16.0));
        BorderPane.setMargin(ip, new Insets(0.0));
        setTop(ip);

        flowPane.getChildren().add(label);
        flowPane.getChildren().add(label0);
        flowPane.getChildren().add(label1);
        anchorPane.getChildren().add(flowPane);
        anchorPane.getChildren().add(stopButton);
        flowPane0.getChildren().add(onlineText);
        flowPane0.getChildren().add(offlineText);
        flowPane0.getChildren().add(ongameText);
        anchorPane.getChildren().add(flowPane0);
        anchorPane.getChildren().add(pieChart);
        anchorPane.getChildren().add(totalLablel);

    }
}
