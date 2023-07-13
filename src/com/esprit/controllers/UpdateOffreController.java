/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.controllers.CrudOffreController;
import com.esprit.entities.Offre;

import com.esprit.services.ServiceDomaineO;
import com.esprit.services.ServiceOffre;
import com.sun.java.swing.plaf.windows.resources.windows;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class UpdateOffreController implements Initializable {

    @FXML
    private TextField txtTitre;
    @FXML
    private DatePicker datePickerExp;
    @FXML
    private TextArea txtDescription;
    @FXML
    private DatePicker datePickerPub;
    @FXML
    private ComboBox<String> ChoiseBoxDomaine;
   
    int idOffre;
    int IDEntreprise;
    ServiceDomaineO sd = new ServiceDomaineO();
    private Stage stage;
    private CrudOffreController offreController;
    private InterfaceOffreEntrepriseController IEntController;
    private InterfaceOffreAdminController InterfaceOffreAdminController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void selectDate(ActionEvent event) {
    }

    @FXML
    private void updateOffre(ActionEvent event) {
        
        if (txtTitre.getText().equals("") || txtDescription.getText().equals("") || datePickerPub.getValue()== null || datePickerExp.getValue()== null || ChoiseBoxDomaine.getValue() == null ) {
            JOptionPane.showMessageDialog(null,"Champ Manquant !");
            return ;
        }
            
            
            
            int result = JOptionPane.showConfirmDialog(null, "vouler vous ajouter un domaine ?","Confirmation",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
                ServiceOffre sf = new ServiceOffre();
                sf.modifier(new Offre(idOffre, txtTitre.getText(), txtDescription.getText(), sd.getIdDomaineByName(ChoiseBoxDomaine.getValue()),IDEntreprise, Date.valueOf(datePickerPub.getValue()), Date.valueOf(datePickerExp.getValue())));
                JOptionPane.showMessageDialog(null, "offre modifier ! "); 
                stage.close();
            }else{
                
                return ;
            }
        
        if (!(offreController == null)) {
            offreController.table();
        }
        if (!(IEntController == null)) {
            IEntController.table();
        }
        if (!(InterfaceOffreAdminController == null)) {
            InterfaceOffreAdminController.table();
        }

    }

    public void setTxtTitre(String txtTitre) {
        this.txtTitre.setText(txtTitre);
    }

    public void setChoiseBoxDomaine() {
        this.ChoiseBoxDomaine.getItems().addAll(sd.getDomainesName());
    }

    

    public void setDatePickerExp(Date date) {
        this.datePickerExp.setValue(date.toLocalDate());
    }

    public void setTxtDescription(String txtDescription) {
        this.txtDescription.setText(txtDescription);;
    }

    public void setDatePickerPub(Date date) {
        this.datePickerPub.setValue(date.toLocalDate());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public int getIDEntreprise() {
        return IDEntreprise;
    }

    public void setIDEntreprise(int IDEntreprise) {
        this.IDEntreprise = IDEntreprise;
    }


    public Stage getStage() {
        return stage;
    }

    public void setOffreController(CrudOffreController offreController) {
        this.offreController = offreController;
    }
    
    public void setOffreEntrController(InterfaceOffreEntrepriseController IEntController) {
        this.IEntController = IEntController;
    }

    public void setInterfaceOffreAdminController(InterfaceOffreAdminController InterfaceOffreAdminController) {
        this.InterfaceOffreAdminController = InterfaceOffreAdminController;
    }
    
    

}
