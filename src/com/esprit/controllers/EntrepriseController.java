/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.Candidat;
import com.esprit.entities.Entreprise;
import com.esprit.services.ServiceUser;
import com.esprit.services.ServiceUser.Entreprisedomaine;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class EntrepriseController implements Initializable, Refresh {

    @FXML
    private TextField txtSearch;
    @FXML
    private Button add;
    @FXML
    private Button search;
    @FXML
    private Button retour;
    @FXML
    private TableView<Entreprisedomaine> table;
    @FXML
    private TableColumn<Entreprisedomaine, String> nomentreprise;
    @FXML
    private TableColumn<Entreprisedomaine, String> nom;
    @FXML
    private TableColumn<Entreprisedomaine, String> prenom;
    @FXML
    private TableColumn<Entreprisedomaine, String> mail;
    @FXML
    private TableColumn<Entreprisedomaine, String> description;
    @FXML
    private TableColumn<Entreprisedomaine, Integer> telephone;
    @FXML
    private TableColumn<Entreprisedomaine, String> taille;
    @FXML
    private TableColumn<Entreprisedomaine, String> secteur;
    @FXML
    private TableColumn<Entreprisedomaine, String> siteweb;
    @FXML
    private TableColumn<Entreprisedomaine, String> linkedin;
    @FXML
    private TableColumn<Entreprisedomaine, Integer> id;
    @FXML
    private TableColumn<Entreprisedomaine, Void> deleteColumn;
    
    ServiceUser su = new ServiceUser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        deleteColumn.setCellFactory(column -> {
            TableCell<Entreprisedomaine, Void> cell = new TableCell<Entreprisedomaine, Void>() {
                private final Button deleteButton = new Button("Supprimer");

                {
                    deleteButton.setOnAction(event -> {
                        Entreprisedomaine c = getTableView().getItems().get(getIndex());
                        if (c != null) {
                            // Afficher la fenêtre contextuelle de confirmation
                            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmation.setTitle("Confirmation");
                            confirmation.setHeaderText("Supprimer l'entreprise ?");
                            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cette Entreprise ?");
                            //L'interface Optional<T> est une fonctionnalité introduite à partir de Java 8 qui permet de représenter une valeur optionnelle qui peut être présente ou absente.
                            //Dans le contexte du code que vous avez partagé, Optional<ButtonType> est utilisé pour représenter le résultat de la boîte de dialogue de confirmation.
                            Optional<ButtonType> result = confirmation.showAndWait();
                            //La méthode showAndWait() de la classe Alert renvoie un objet Optional<ButtonType>. Cet objet Optional contient le résultat de la boîte de dialogue (le bouton sur lequel l'utilisateur a cliqué) s'il est présent, sinon il est vide.
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                // Supprimer le candidat de la liste
                                table.getItems().remove(c);
                                su.supprimer(c);
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };

            return cell;
        });

        id.setVisible(false);
        id.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("nom"));
        nomentreprise.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("NomEntreprise"));
        telephone.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,Integer>("numero_telephone"));
        prenom.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("prenom"));
        siteweb.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("SiteWeb"));
        mail.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("description"));
        linkedin.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("Linkedin"));
        taille.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("TailleEntreprise"));
        secteur.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("nom_domaine"));
        ObservableList<Entreprisedomaine> lu = FXCollections.observableArrayList();
        try{
            lu.addAll(su.afficherentreprise());
        }catch(Exception e){
            e.printStackTrace();
        }
        table.setItems(lu);
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Entreprisedomaine selectedUser = table.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ModifierEntreprise.fxml"));
                    Parent root = loader.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                    ModifierEntrepriseController controller = loader.getController();
                    controller.initData(selectedUser);
                    controller.setRefreshEvent(this);
                    controller.setPrimarystage(stage);
                }catch(Exception e){
                    e.getMessage();
                }
            }   
            }
});
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> Search());
    }    
    @FXML
    public void AjouterEntreprise(ActionEvent event) throws IOException {
    

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AjoutEntreprise.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            AjoutEntrepriseController controller = loader.getController();
            controller.setRefreshEvent(this);
            controller.setPrimarystage(stage);
      
}
    @FXML
    private void deleteButtonClicked() {
        
        // Obtenir l'élément sélectionné dans la TableView
        Entreprisedomaine selectedCandidat = table.getSelectionModel().getSelectedItem();
        
        if (selectedCandidat  != null) {
            // Afficher la fenêtre contextuelle de confirmation
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Supprimer l'élément ?");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet élément ?");
            
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Supprimer l'élément de la liste
                    table.getItems().remove(selectedCandidat );
                    su.supprimer(selectedCandidat);
                }
            });
        } else {
            // Aucun élément sélectionné, afficher une alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun élément sélectionné");
            alert.setContentText("Veuillez sélectionner un élément à supprimer.");
            alert.showAndWait();
        }
    }

    public void onRefresh(){
        id.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("nom"));
        nomentreprise.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("NomEntreprise"));
        prenom.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("prenom"));
        siteweb.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("SiteWeb"));
        mail.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("description"));
        linkedin.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("Linkedin"));
        taille.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("Taille"));
        secteur.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("nom_domaine"));
        ObservableList<Entreprisedomaine> lu = FXCollections.observableArrayList();
        try{
            lu.addAll(su.afficherentreprise());
        }catch(Exception e){
            e.printStackTrace();
        }
        table.setItems(lu);
}
    public void Retour(ActionEvent e){
        id.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("nom"));
        nomentreprise.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("NomEntreprise"));
        prenom.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("prenom"));
        siteweb.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("SiteWeb"));
        mail.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("description"));
        linkedin.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("Linkedin"));
        taille.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("Taille"));
        secteur.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("nom_domaine"));
        ObservableList<Entreprisedomaine> lu = FXCollections.observableArrayList();
        try{
            lu.addAll(su.afficherentreprise());
        }catch(Exception a){
            a.printStackTrace();
        }
        table.setItems(lu);
    }
    
    public void Search(){
        id.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("nom"));
        nomentreprise.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("NomEntreprise"));
        prenom.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("prenom"));
        siteweb.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("SiteWeb"));
        mail.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("description"));
        linkedin.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("Linkedin"));
        taille.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("Taille"));
        secteur.setCellValueFactory(new PropertyValueFactory<Entreprisedomaine,String>("nom_domaine"));
        ObservableList<Entreprisedomaine> lu = FXCollections.observableArrayList();
        try{
            lu.addAll(su.searchentreprise(txtSearch.getText()));
        }catch(Exception n){
            n.printStackTrace();
        }
        table.setItems(lu);
    }
     
    
}
