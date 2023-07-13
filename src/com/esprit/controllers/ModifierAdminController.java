/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.Administrateur;
import com.esprit.entities.Candidat;
import com.esprit.entities.MailException;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class ModifierAdminController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tftelephone;
    @FXML
    private TextArea tadescription;
    @FXML
    private TextField tfmp;
    @FXML
    private TextField tfmp2;
    @FXML
    private Button submit;
    private Refresh refreshEvent;
     private Integer id =0;
     private Stage primarystage;

    public void setPrimarystage(Stage primarystage) {
        this.primarystage = primarystage;
    }

    public void setRefreshEvent(Refresh refreshListener) {
        this.refreshEvent = refreshListener;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    public void triggerRefreshEvent() {
        if (refreshEvent != null) {
            refreshEvent.onRefresh();
        }
    }
    public boolean validateFields() {
        if (tfnom.getText().isEmpty() || tfprenom.getText().isEmpty() || tfadresse.getText().isEmpty() || tfmp.getText().isEmpty() || tfmp2.getText().isEmpty() || tftelephone.getText().isEmpty() || tadescription.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs obligatoires");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    public void initData(Administrateur admin) {
        id = admin.getId();
        tfnom.setText(admin.getNom());
        tfprenom.setText(admin.getPrenom());
        tfadresse.setText(admin.getMail());
        tftelephone.setText(String.valueOf(admin.getNumero_telephone()));
        tadescription.setText(admin.getDescription());
        
        
    }
    
    @FXML
    public void Modifieradmin(ActionEvent event) throws IOException, MailException {
        if (!validateFields()) {
            return;
        }
        
        ServiceUser sp = new ServiceUser();
        if (!Administrateur.emailvalidator(tfadresse.getText())) {
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
        } else if (tfmp.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("le mot de passe ne doit pas être inferieur à 8 caractéres");
            alert.showAndWait();
            return;
        }
        List<Administrateur> list = sp.afficherAdmin();
        Boolean adminexiste = false;
        for (Administrateur u : list) {
            if (u.getMail().equals(tfadresse.getText()) || u.getNumero_telephone() == Integer.parseInt(tftelephone.getText())) {
                adminexiste = true;
                break;
            }
        }
        if (adminexiste) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Admin existe déja");
            alert.showAndWait();
            return;

        }
        if (tfmp.getText().equals(tfmp2.getText())) {
            sp.modifier(new Administrateur(id,tfnom.getText(), tfprenom.getText(), tfadresse.getText(), Integer.parseInt(tftelephone.getText()), tfmp.getText(), tadescription.getText()));
            JOptionPane.showMessageDialog(null, "Admin modifié !");
            triggerRefreshEvent();
            primarystage.close();

        } else {
            JOptionPane.showMessageDialog(null, "Mot de passe erronée");
        }
    }
    
}
