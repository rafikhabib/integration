/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.*;
import com.esprit.services.ServiceCompetence;
import com.esprit.services.ServiceDomaine;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class AjoutCandidatController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfmotdepasse;
    @FXML
    private TextField tfmotdepasse2;
    @FXML
    private TextField tftelephone;
    @FXML
    private TextField tfgithub;
    @FXML
    private TextArea tadescription;
    @FXML
    private ComboBox<Experience> cbexperience;
    @FXML
    private ComboBox<Diplome> cbdiplome;
    @FXML
    private ListView<String> listcompetence;
    @FXML
    private Button envoye;
    private Stage primarystage;

    public void setPrimarystage(Stage primarystage) {
        this.primarystage = primarystage;
    }

    private Refresh refreshEvent;

    public void setRefreshEvent(Refresh refreshListener) {
        this.refreshEvent = refreshListener;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceCompetence sc = new ServiceCompetence();
        cbdiplome.setItems(FXCollections.observableArrayList(Diplome.values()));
        cbexperience.setItems(FXCollections.observableArrayList(Experience.values()));
        listcompetence.setItems(FXCollections.observableArrayList(sc.affichercompetencebynom()));
        listcompetence.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void triggerRefreshEvent() {
        if (refreshEvent != null) {
            refreshEvent.onRefresh();
        }
    }

    @FXML
    public void ajouterCandidat(ActionEvent event) throws IOException, MailException {
        if (!validateFields()) {
            return;
        }
        
        ServiceUser sp = new ServiceUser();
        if (!Candidat.emailvalidator(tfadresse.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Mail invalide");
            alert.showAndWait();
            return;
        } else if (tftelephone.getText().length() != 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("le numero de téléphone doit être composé de 8 chiffres");
            alert.showAndWait();
            return;
        } else if (tfmotdepasse.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("le mot de passe ne doit pas être inferieur à 8 caractéres");
            alert.showAndWait();
            return;
        }
        List<Candidat> list = sp.afficherCandidat();
        Boolean candidatexiste = false;
        for (Candidat u : list) {
            if (u.getMail().equals(tfadresse.getText()) || u.getNumero_telephone() == Integer.parseInt(tftelephone.getText())) {
                candidatexiste = true;
                break;
            }
        }
        if (candidatexiste) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Candidat existe déja");
            alert.showAndWait();
            return;

        }
        if (tfmotdepasse.getText().equals(tfmotdepasse2.getText())) {
            sp.ajouter(new Candidat(tfnom.getText(), tfprenom.getText(), tfadresse.getText(), Integer.parseInt(tftelephone.getText()), tfmotdepasse.getText(), tadescription.getText(), cbdiplome.getValue(), tfgithub.getText(), cbexperience.getValue()));
            JOptionPane.showMessageDialog(null, "Candidat ajouté !");
            triggerRefreshEvent();
            primarystage.close();

        } else {
            JOptionPane.showMessageDialog(null, "Mot de passe erronée");
        }
    }

    public boolean validateFields() {
        if (tfnom.getText().isEmpty() || tfprenom.getText().isEmpty() || tfadresse.getText().isEmpty() || tfmotdepasse.getText().isEmpty() || tfmotdepasse2.getText().isEmpty() || tftelephone.getText().isEmpty() || tfgithub.getText().isEmpty() || tadescription.getText().isEmpty() || cbexperience.getValue() == null || cbdiplome.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs obligatoires");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
