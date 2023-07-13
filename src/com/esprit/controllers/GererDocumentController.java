package com.esprit.controllers;

import com.esprit.entities.Document;
import com.esprit.services.ServiceGererDocument;
import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GererDocumentController implements Initializable {

    @FXML
    private TextField txtTitre;

    private TextField txtUserId;

    @FXML
    private ChoiceBox<String> choiceType;

    @FXML
    private TextField txtLien;

    @FXML
    private TableView<Document> tblDocuments;

    @FXML
    private TableColumn<Document, String> colTitre;
    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Document, String> colType;

    @FXML
    private TableColumn<Document, String> colLien;

    @FXML
    private Button btnSupprimer;

    private ObservableList<Document> documentsList;
    private ServiceGererDocument serviceGererDocument;
    @FXML
    private Color linearGradientEnd;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnChooseFile;
    @FXML
    private TextField txtFilePath;
    @FXML
    private Button btnUpload;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadChoiceBox();
        refresh();

        tblDocuments.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Document selectedDocument = tblDocuments.getSelectionModel().getSelectedItem();
                txtTitre.setText(selectedDocument.getTitre_document());
                choiceType.getSelectionModel().select(selectedDocument.getType());
                txtLien.setText(selectedDocument.getLien());

            }
        });
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> search());

    }

    @FXML
    public void ajouter(ActionEvent event) {
        String titre = txtTitre.getText();
        String description = "";
        String type = choiceType.getValue();
        String lien = txtLien.getText();

        // Check if the title already exists
        if (isTitleExists(titre)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Title already exists");
            alert.showAndWait();
            return;
        }

        // Create a new Document instance
        Document document = new Document();
        document.setTitre_document(titre);
        document.setDescription_document(description);
        document.setType(type);
        document.setLien(lien);
        document.setId_user(1);
        ServiceGererDocument sd = new ServiceGererDocument();

        sd.ajouter(document);
        refresh();
        JOptionPane.showMessageDialog(null, "Document ajouté !");
    }

    @FXML
    void modifier(ActionEvent event) {
        Document selectedDocument = tblDocuments.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            String titre = txtTitre.getText();
            String description = "";
            String type = choiceType.getValue();
            String lien = txtLien.getText();

            if (titre.isEmpty() || type.isEmpty() || lien.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all the fields");
                alert.showAndWait();
                return;
            }

            selectedDocument.setTitre_document(titre);
            selectedDocument.setDescription_document(description);
            selectedDocument.setType(type);
            selectedDocument.setLien(lien);
            selectedDocument.setId_user(1);

            serviceGererDocument.modifier(selectedDocument);
            tblDocuments.refresh();
            clearFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a document to edit");
            alert.showAndWait();
        }

        JOptionPane.showMessageDialog(null, "Document Modifié !");
    }

    @FXML
    void supprimer(ActionEvent event) {
        Document selectedDocument = tblDocuments.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            serviceGererDocument.supprimer(selectedDocument);
            documentsList.remove(selectedDocument);
            clearFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a document to delete");
            alert.showAndWait();
        }

        JOptionPane.showMessageDialog(null, "Document Supprimé !");
    }

    public void search() {
        String searchTerm = txtSearch.getText();

        ObservableList<Document> searchResults = FXCollections.observableArrayList();

        for (Document document : documentsList) {
            if (document.getTitre_document().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchResults.add(document);
            }

        }

        tblDocuments.setItems(searchResults);
    }

    public void loadChoiceBox() {
        ObservableList<String> types = FXCollections.observableArrayList("Type 1", "Type 2", "Type 3");
        choiceType.setItems(types);
    }

    public void clearFields() {
        txtTitre.clear();
        choiceType.getSelectionModel().clearSelection();
        txtLien.clear();
    }

    public void refresh() {

        serviceGererDocument = new ServiceGererDocument();

        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre_document"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colLien.setCellValueFactory(new PropertyValueFactory<>("lien"));

        documentsList = FXCollections.observableArrayList(serviceGererDocument.afficher());
        tblDocuments.setItems(documentsList);
    }

    @FXML
    public void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) tblDocuments.getScene().getWindow(); // Get the current stage
        File file = fileChooser.showOpenDialog(stage); // Show the file chooser dialog
        if (file != null) {
            // Set the chosen file path to the text field
            txtFilePath.setText(file.getAbsolutePath());
        }
    }

    public boolean isTitleExists(String titre) {
        for (Document document : documentsList) {
            if (document.getTitre_document().equalsIgnoreCase(titre)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void uploadFile(ActionEvent event) {
        String filePath = txtFilePath.getText();
        if (!filePath.isEmpty()) {
            File sourceFile = new File(filePath);
            String fileName = sourceFile.getName();

            // Set the destination directory where the file should be stored
            String destinationDirectory = "C:/xampp/htdocs/PI Files/";

            try {
                // Create a new File object for the destination directory
                File destinationDir = new File(destinationDirectory);

                // Check if the destination directory exists
                if (!destinationDir.exists()) {
                    destinationDir.mkdirs(); // Create the directory if it doesn't exist
                }

                // Create a new File object for the destination file
                File destinationFile = new File(destinationDirectory + fileName);

                // Copy the source file to the destination file
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Show a success message
                JOptionPane.showMessageDialog(null, "File uploaded successfully. It will be reviewed by the administrator.");

                // Clear the file chooser field
                txtFilePath.clear();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while uploading the file.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please choose a file to upload.");
        }
    }

    @FXML
    public void loadStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Statistics.fxml"));
            Parent statisticsRoot = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Statistics");
            stage.setScene(new Scene(statisticsRoot));

            // Get the current stage
            Stage currentStage = (Stage) tblDocuments.getScene().getWindow();
            currentStage.hide(); // Hide the current stage

            // Set the onHidden event for the statistics stage
            stage.setOnHidden(event -> currentStage.show()); // Show the previous stage when statistics stage is closed

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
