/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.Candidature;
import com.esprit.entities.EtatCandidature;
import com.esprit.entities.MailException;
import com.esprit.entities.User;
import com.esprit.services.ServiceCandidature;
import com.esprit.services.ServiceMailO;
import com.esprit.services.ServiceUser;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CrudCandidatureEntrepriseController implements Initializable {

   @FXML
    private TableView<ServiceCandidature.CandidatureDetails> tableCandidature;
    @FXML
    private TableColumn<ServiceCandidature.CandidatureDetails, String> dateCol;
    @FXML
    private TableColumn<ServiceCandidature.CandidatureDetails, String> nomCol;
    @FXML
    private TableColumn<ServiceCandidature.CandidatureDetails, String> prenomCol;
    @FXML
    private TableColumn<ServiceCandidature.CandidatureDetails, String> entrepriseCol;
    @FXML
    private TableColumn<ServiceCandidature.CandidatureDetails, String> posteCol;
    @FXML
    private TableColumn<ServiceCandidature.CandidatureDetails, String>domaineCol;
    @FXML
    private TableColumn<ServiceCandidature.CandidatureDetails, EtatCandidature> etatCol;
    ServiceCandidature sc = new ServiceCandidature();
    @FXML
    private TableColumn gererCandCol;
    private int idEntreprise = 5;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      

        refreshTable();
        
       
    }    
    
    
    public void refreshTable(){
        ObservableList<ServiceCandidature.CandidatureDetails> listCandidature = (ObservableList<ServiceCandidature.CandidatureDetails>) FXCollections.observableArrayList(sc.candidatureDetailsParEntreprise(idEntreprise));
        System.out.println(sc.candidatureDetailsParEntreprise(idEntreprise));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date_condidature"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom_user"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom_user"));
        entrepriseCol.setCellValueFactory(new PropertyValueFactory<>("nom_entreprise"));
        posteCol.setCellValueFactory(new PropertyValueFactory<>("titreoffre"));
        domaineCol.setCellValueFactory(new PropertyValueFactory<>("nom_domaine"));
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));

        Callback<TableColumn<ServiceCandidature.CandidatureDetails,String>,TableCell<ServiceCandidature.CandidatureDetails,String>> cellFactory =(param) -> {
            final TableCell<ServiceCandidature.CandidatureDetails,String> cell = new TableCell<ServiceCandidature.CandidatureDetails,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        final Button acceptBtn = new Button("Accepter");
                        final Button refuserBtn = new Button("Refuser");
                        deleteIcon.setStyle(
                                "-fx-cursor:hand;"
                                + "-glyph-size:35px;"
                                + "-fx-fill:#ff1744;"
                                
                        );
                        acceptBtn.setStyle(
                                "-fx-cursor:hand;"
                                + "-fx-background-color:#16da78;"
                                + "-fx-text-fill: white;"
                                + "-fx-fill:#ff1744;"
                                + "-fx-padding: 5 10 5 10;"
                                + "-fx-font-size: 18px;"
                                
                        );
                        refuserBtn.setStyle(
                                "-fx-cursor:hand;"
                                + "-fx-background-color:#ff6600;"
                                + "-fx-text-fill: white;"
                                + "-fx-fill:#ff1744;"
                                + "-fx-padding: 5 10 5 10;"
                                + "-fx-font-size: 18px;"
                                
                        );
                        
                        
                        deleteIcon.setOnMouseClicked((MouseEvent event) ->{
                        ServiceCandidature.CandidatureDetails cand = getTableView().getItems().get(getIndex());
                            supprimerCandidature(cand);
                        
                        });
                        acceptBtn.setOnAction((event) -> {
                            ServiceCandidature.CandidatureDetails cand = getTableView().getItems().get(getIndex());
                            try {
                                accpeter(cand);
                            } catch (MailException ex) {
                                
                            }
                        });
                        
                        refuserBtn.setOnAction((event) -> {
                            ServiceCandidature.CandidatureDetails cand = getTableView().getItems().get(getIndex());
                            try {
                                refuser(cand);
                            } catch (MailException ex) {
                                
                            }
                        });
                       
                
                        HBox managebtn = new HBox(acceptBtn,refuserBtn,deleteIcon);
                        managebtn.setStyle("-fx-alignement:center");
                        HBox.setMargin(deleteIcon, new Insets(2,25,0,2));
                        HBox.setMargin(acceptBtn, new Insets(2,15,0,2));
                        HBox.setMargin(refuserBtn, new Insets(2,15,0,2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell; 
        };
        
        gererCandCol.setCellFactory(cellFactory);
        tableCandidature.setItems(listCandidature);

        
    }


    
private void supprimerCandidature(ServiceCandidature.CandidatureDetails cand) {
        int id_user = cand.getId_user();
        int id_can = cand.getId_candidature();
        int id_offre = cand.getId_offre();
        Date date_condidature = cand.getDate_condidature();
        EtatCandidature etat =cand.getEtat();
        
        int result = JOptionPane.showConfirmDialog(null, "vouler vous supprimer cet Candidature ?","Confirmation",JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION){
            sc.supprimer(new Candidature(id_user, id_offre,  id_can, date_condidature, etat));
            refreshTable();

        }else{
            return ;
        }
        
    }

private void accpeter(ServiceCandidature.CandidatureDetails cand) throws MailException {

        int id_user = cand.getId_user();
        int id_can = cand.getId_candidature();
        int id_offre = cand.getId_offre();

        Date date_condidature = cand.getDate_condidature();
        int result = JOptionPane.showConfirmDialog(null, "vouler vous accepter ce candidat ?","Confirmation",JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION){
            sc.modifier(new Candidature(id_user, id_offre, id_can, date_condidature, EtatCandidature.Accepter));
            JOptionPane.showMessageDialog(null, "Candidat Accepter  !");
            refreshTable();


            ServiceUser su = new ServiceUser();
            User u = su.getUserByID(id_user);
            String nomEntreprise = cand.getNom_entreprise();

            String titre = cand.getTitreoffre();

            String recepient =u.getMail();
            String object=nomEntreprise;
            String message="Votre Candidature dans poste "+titre+" a ete accepter";
            try {
                ServiceMailO.sendMail(recepient, object, message);
            } catch (MessagingException ex) {
                System.out.println(ex.getMessage());
            }
        }else{
            return ;
        }
        
    }

private void refuser(ServiceCandidature.CandidatureDetails cand) throws MailException {
        int id_user = cand.getId_user();
        int id_can = cand.getId_candidature();
        int id_offre = cand.getId_offre();
        Date date_condidature = cand.getDate_condidature();

        int result = JOptionPane.showConfirmDialog(null, "vouler vous refuser ce candidat ?","Confirmation",JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION){
            sc.modifier(new Candidature(id_user, id_offre, id_can, date_condidature, EtatCandidature.Refuser));
            JOptionPane.showMessageDialog(null, "Candidat refuser  !");
            refreshTable();

            ServiceUser su = new ServiceUser();
            User u = su.getUserByID(id_user);
            String nomEntreprise = cand.getNom_entreprise();

            String titre = cand.getTitreoffre();

            String recepient =u.getMail();
            String object=nomEntreprise;
            String message="Votre Candidature dans poste "+titre+" a ete refuser";
            try {
                ServiceMailO.sendMail(recepient, object, message);
            } catch (MessagingException ex) {
                System.out.println(ex.getMessage());
            }
        }else{
            return ;
        }
        
        
    } 
    
}
