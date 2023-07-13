/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.Candidature;
import com.esprit.services.ServiceCandidature;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static jdk.internal.dynalink.support.Guards.isNull;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class OffreDetailsController implements Initializable {

    @FXML
    private Label LNomEntreprise;
    @FXML
    private Label LNomDomaine;
    @FXML
    private Label LDateExp;
    @FXML
    private Label LDatePub;
    @FXML
    private TextArea LDesc;
    @FXML
    private Label LTitle;
    
    private int idoffre;
    Stage stage;
    @FXML
    private Label dejaPostuler;
    
    int idu;
ServiceCandidature sc = new ServiceCandidature();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        showMsg();
    }    
    
    public void showMsg(){
        System.out.println(sc.checkCandidature(idu, idoffre));
        if (!sc.checkCandidature(idu, idoffre)) {
                dejaPostuler.setText("deja Postuler");
        }else{
        dejaPostuler.setText("");

        }
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }
    

    public void setLNomEntreprise(String LNomEntreprise) {
        this.LNomEntreprise.setText(LNomEntreprise);
    }

    public void setLNomDomaine(String LNomDomaine) {
        this.LNomDomaine.setText(LNomDomaine);
    }

    public void setLDateExp(String LDateExp) {
        this.LDateExp.setText(LDateExp);
    }

    public void setLDatePub(String LDatePub) {
        this.LDatePub.setText(LDatePub);
    }

    public void setLDesc(String LDesc) {
        this.LDesc.setText(LDesc);
    }

    public void setLTitle(String LTitle) {
        this.LTitle.setText(LTitle);
    }

    public int getIdoffre() {
        return idoffre;
    }

    public void setIdoffre(int idoffre) {
        this.idoffre = idoffre;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void postuler(ActionEvent event) {
        System.out.println(idoffre);
        if (! sc.checkCandidature(idu, idoffre)) {
            JOptionPane.showMessageDialog(null, "Vous Aver Deja Postuler Dans Cet Offre!");
            return ;
        }
        int result = JOptionPane.showConfirmDialog(null, "vouler vous postuler?","Confirmation",JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION){
                sc.ajouter(new Candidature(idu, idoffre));
                JOptionPane.showMessageDialog(null, "Postuler avec Succes !");
                stage.close();
            }else{
            return;
            }
    }
    
    
    
}
