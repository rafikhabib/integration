package com.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private PieChart chart;

    private ObservableList<String> selectedTypes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the statistics interface
        initializeChart();
    }

    private void initializeChart() {
        // Get the data from the table document
        List<Document> documentData = getDocumentData();

        // Calculate the count of each type
        Map<String, Integer> typeCounts = calculateTypeCounts(documentData);

        // Generate the statistics data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (String type : typeCounts.keySet()) {
            int count = typeCounts.get(type);
            pieChartData.add(new PieChart.Data(type, count));
        }

        // Set the data to the chart
        chart.setData(pieChartData);
        chart.setLegendVisible(false);
    }

    private List<Document> getDocumentData() {
    List<Document> documentData = new ArrayList<>();
    documentData.add(new Document("Type 1"));
    documentData.add(new Document("Type 2"));
    documentData.add(new Document("Type 1"));
    documentData.add(new Document("Type 3"));
    documentData.add(new Document("Type 2"));
    documentData.add(new Document("Type 3"));
    return documentData;
}

    private Map<String, Integer> calculateTypeCounts(List<Document> documentData) {
        // Calculate the count of each type from the document data
        Map<String, Integer> typeCounts = new HashMap<>();
        for (Document document : documentData) {
            String type = document.getType();
            typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
        }
        return typeCounts;
    }

    public void setSelectedTypes(List<String> selectedTypes) {
        this.selectedTypes = FXCollections.observableArrayList(selectedTypes);
    }

    private void goBack() {
        // Handle the "Back" button click
        // You can redirect to the previous interface or perform any other action
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Back");
        alert.setHeaderText(null);
        alert.setContentText("Redirecting back to the previous interface.");
        alert.showAndWait();
    }

    // Replace Document with your actual document class
    private static class Document {
        private String type;

        public Document(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
