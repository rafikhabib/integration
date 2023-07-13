/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.Candidat;
import com.esprit.entities.Domaine;
import com.esprit.entities.Entreprise;
import com.esprit.entities.Experience;
import com.esprit.entities.MailException;
import com.esprit.entities.Taille;
import com.esprit.services.ServiceDomaine;
import com.esprit.services.ServiceUser;
import com.esprit.services.ServiceUser.Entreprisedomaine;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class ModifierEntrepriseController implements Initializable {

    @FXML
    private AnchorPane ancho;
    @FXML
    private TextField tfNomEntreprise;
    @FXML
    private TextField tfAdresse;
    @FXML
    private TextField tfnumero;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfSIteWeb;
    @FXML
    private TextField tfLinkedin;
    @FXML
    private TextArea taDescription;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfPassword2;
    @FXML
    private ComboBox<String> cbSecteurActivite;
    @FXML
    private Button modifier;
    @FXML
    private ComboBox<Taille> cbTailleEntreprise;
    private Refresh refreshEvent;
     private Stage primarystage;

    public void setPrimarystage(Stage primarystage) {
        this.primarystage = primarystage;
    }
    private Integer id =0;
    public void setRefreshEvent(Refresh refreshListener) {
    this.refreshEvent = refreshListener;
    ServiceDomaine sd = new ServiceDomaine();
}
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceUser su = new ServiceUser();
       cbTailleEntreprise.setItems(FXCollections.observableArrayList(Taille.values()));
       cbSecteurActivite.setItems(FXCollections.observableArrayList(su.afficherDomainebynom()));
    }  
    
    public void initData(Entreprise entreprise) {
        ServiceDomaine sd = new ServiceDomaine();
        id = entreprise.getId();
        tfNomEntreprise.setText(entreprise.getNomEntreprise());
        tfNom.setText(entreprise.getNom());
        tfPrenom.setText(entreprise.getPrenom());
        tfAdresse.setText(entreprise.getMail());
        tfnumero.setText(String.valueOf(entreprise.getNumero_telephone()));
        tfLinkedin.setText(entreprise.getLinkedin());
        taDescription.setText(entreprise.getDescription());
        tfSIteWeb.setText(entreprise.getSiteWeb());
        cbTailleEntreprise.setValue(entreprise.getTailleEntreprise());
        cbSecteurActivite.setValue(sd.getNameDomaineById(entreprise.getId_domaine()));
       
        
    }
     public void triggerRefreshEvent() {
        if (refreshEvent != null) {
            refreshEvent.onRefresh();
        }
     }
     public boolean validateFields() {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfNomEntreprise.getText().isEmpty() || tfnumero.getText().isEmpty() || tfPassword.getText().isEmpty() || tfPassword2.getText().isEmpty() || tfSIteWeb.getText().isEmpty() || tfLinkedin.getText().isEmpty() || taDescription.getText().isEmpty() || tfAdresse.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs obligatoires");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    @FXML
    public void ModifierEntreprise(ActionEvent event) throws IOException, MailException {
        if(!validateFields()){
            return;
        }
        Candidat c = new Candidat();
        ServiceUser sp = new ServiceUser();
        if (!c.emailvalidator(tfAdresse.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Mail invalide");
            alert.showAndWait();
            return;
        } else if (tfnumero.getText().length() != 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("le numero de téléphone doit être composé de 8 chiffres");
            alert.showAndWait();
            return;
        } else if (tfPassword.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("le mot de passe ne doit pas être inferieur à 8 caractéres");
            alert.showAndWait();
            return;
        }
        List<ServiceUser.Entreprisedomaine> list = sp.afficherentreprise();
        Boolean entreprisetexiste = false;
        for (ServiceUser.Entreprisedomaine u : list) {
            if (u.getMail().equals(tfAdresse.getText()) || u.getNumero_telephone() == Integer.parseInt(tfnumero.getText()) || u.getNomEntreprise().equals(tfNomEntreprise.getText())) {
                entreprisetexiste = true;
                break;
            }
        }
        if (entreprisetexiste) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Entreprise existe déja");
            alert.showAndWait();
            return;
        }
        ServiceDomaine sd = new ServiceDomaine();
        if(tfPassword.getText().equals(tfPassword2.getText())){
        sp.modifier(new Entreprise(id,tfNom.getText(), tfPrenom.getText(), tfAdresse.getText(), Integer.parseInt(tfnumero.getText()), tfPassword.getText(), taDescription.getText(),tfNomEntreprise.getText(),cbTailleEntreprise.getValue(),tfSIteWeb.getText(),tfLinkedin.getText(),sd.getIdDomaineByName(cbSecteurActivite.getValue())));
        JOptionPane.showMessageDialog(null, "Entreprise modifiée !");
        triggerRefreshEvent();
        primarystage.close();
        }
        else {
            JOptionPane.showMessageDialog(null, "Mot de passe erronée");
        }
    }
    
    
}
