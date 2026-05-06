package com.mycompany.javafxminiproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML private BarChart<String, Number> barChart;
    @FXML private ListView<String[]> userListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupBarChart();
        setupUserList();
        styleBarChart();
    }

    private void setupBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Jan", 45));
        series.getData().add(new XYChart.Data<>("Feb", 60));
        series.getData().add(new XYChart.Data<>("Mar", 40));
        series.getData().add(new XYChart.Data<>("Apr", 80));
        series.getData().add(new XYChart.Data<>("May", 55));
        series.getData().add(new XYChart.Data<>("Jun", 70));
        barChart.getData().add(series);

        // Warnai setiap bar
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: #4f8ef7;");
                }
            });
        }
    }

    private void styleBarChart() {
        barChart.lookup(".chart-plot-background")
                .setStyle("-fx-background-color: transparent;");
        barChart.lookupAll(".chart-vertical-grid-lines")
                .forEach(n -> n.setStyle("-fx-stroke: rgba(255,255,255,0.05);"));
        barChart.lookupAll(".chart-horizontal-grid-lines")
                .forEach(n -> n.setStyle("-fx-stroke: rgba(255,255,255,0.05);"));
        barChart.lookupAll(".axis")
                .forEach(n -> n.setStyle("-fx-tick-label-fill: #6b7a99; -fx-color: #6b7a99;"));
    }

    private void setupUserList() {
        // Data: [nama, role, status, warna avatar, inisial]
        String[][] users = {
            {"Budi Raharjo",  "Admin",  "Active", "#4f8ef7", "BR"},
            {"Siti Aisyah",   "Editor", "Active", "#7c5cfc", "SA"},
            {"Dimas Hendra",  "Viewer", "Away",   "#f6a041", "DH"},
            {"Rina Puspita",  "Admin",  "Active", "#28c840", "RP"},
            {"Ahmad Fauzi",   "Editor", "Offline","#e05c5c", "AF"},
        };

        userListView.getItems().addAll(users);

        userListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String[] user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                    return;
                }

                // Avatar lingkaran
                StackPane avatar = new StackPane();
                Circle circle = new Circle(18);
                circle.setStyle("-fx-fill: " + user[3] + "30;");
                Label initials = new Label(user[4]);
                initials.setStyle("-fx-text-fill: " + user[3] + "; -fx-font-size: 11; -fx-font-weight: bold;");
                avatar.getChildren().addAll(circle, initials);

                // Info nama + role
                VBox info = new VBox(2);
                Label name = new Label(user[0]);
                name.setStyle("-fx-text-fill: #c8d0e7; -fx-font-size: 12; -fx-font-weight: bold;");
                Label role = new Label(user[1]);
                role.setStyle("-fx-text-fill: #6b7a99; -fx-font-size: 11;");
                info.getChildren().addAll(name, role);

                // Badge status
                String badgeColor = user[2].equals("Active") ? "#28c840"
                                  : user[2].equals("Away")   ? "#f6a041"
                                  : "#6b7a99";
                Label badge = new Label(user[2]);
                badge.setStyle(
                    "-fx-text-fill: " + badgeColor + ";" +
                    "-fx-background-color: " + badgeColor + "20;" +
                    "-fx-background-radius: 20;" +
                    "-fx-font-size: 11;" +
                    "-fx-padding: 2 10 2 10;"
                );

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                HBox row = new HBox(10);
                row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                row.getChildren().addAll(avatar, info, spacer, badge);
                row.setStyle("-fx-padding: 6 0 6 0;");

                setGraphic(row);
                setStyle("-fx-background-color: transparent;");
            }
        });
    }
}