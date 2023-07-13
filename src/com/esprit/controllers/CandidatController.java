/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.Candidat;
import com.esprit.entities.Diplome;
import com.esprit.entities.Experience;
import com.esprit.entities.MailException;
import com.esprit.entities.Taille;
import com.esprit.entities.User;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class CandidatController implements Initializable, Refresh {

    @FXML
    private TableColumn<Candidat, Integer> id;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button add;
    @FXML
    private Button search;
    @FXML
    private Button retour;
    @FXML
    private TableColumn<Candidat, String> nom;
    @FXML
    private TableColumn<Candidat, String> prenom;
    @FXML
    private TableColumn<Candidat, String> mail;
    @FXML
    private TableColumn<Candidat, Integer> phone;
    @FXML
    private TableColumn<Candidat, String> description;
    @FXML
    private TableColumn<Candidat, String> education;
    @FXML
    private TableColumn<Candidat, String> experience;
    @FXML
    private TableColumn<Candidat, String> github;
    @FXML
    private TableColumn<Candidat, Void> deleteColumn;

    @FXML
    private TableView<Candidat> table;

    ServiceUser su = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //interface fonctionnelle
        
        
        deleteColumn.setCellFactory(column -> {
            TableCell<Candidat, Void> cell = new TableCell<Candidat, Void>() {
                //La cellule contient un bouton de suppression déclaré et initialisé en tant que Button avec le texte "Supprimer".
                private final Button deleteButton = new Button("Supprimer");

                {
                    //Un gestionnaire d'événements est défini sur le bouton en utilisant setOnAction. Lorsque le bouton est cliqué, le code à l'intérieur du bloc est exécuté.
                    deleteButton.setOnAction(event -> {
                        //Cette ligne récupère l'élément (Entreprisedomaine) correspondant à la ligne de la cellule actuelle. getTableView() renvoie la référence de la table associée à la cellule, et getItems() renvoie la liste des éléments affichés dans la table. getIndex() renvoie l'index de la ligne de la cellule actuelle.
                        Candidat candidat = getTableView().getItems().get(getIndex());
                        if (candidat != null) {
                            // Afficher la fenêtre contextuelle de confirmation
                            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmation.setTitle("Confirmation");
                            confirmation.setHeaderText("Supprimer le candidat ?");
                            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce candidat ?");
                            //Cette ligne affiche la boîte de dialogue de confirmation et attend que l'utilisateur fasse un choix. La méthode showAndWait() suspend l'exécution jusqu'à ce que l'utilisateur ferme la boîte de dialogue.
                            //L'interface Optional<T> est une fonctionnalité introduite à partir de Java 8 qui permet de représenter une valeur optionnelle qui peut être présente ou absente. Dans le contexte du code que vous avez partagé, Optional<ButtonType> est utilisé pour représenter le résultat de la boîte de dialogue de confirmation.
                            //La méthode showAndWait() de la classe Alert renvoie un objet Optional<ButtonType>. Cet objet Optional contient le résultat de la boîte de dialogue (le bouton sur lequel l'utilisateur a cliqué) s'il est présent, sinon il est vide.
                            Optional<ButtonType> result = confirmation.showAndWait();
                            //Cette partie du code vérifie si l'utilisateur a fait un choix et si le choix est égal à ButtonType.OK (le bouton OK de la boîte de dialogue de confirmation)
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                // Supprimer le candidat de la liste
                                table.getItems().remove(candidat);
                                su.supprimer(candidat);
                            }
                        }
                    });
                }
                // Cette méthode updateItem est une méthode de la classe TableCell qui est appelée chaque fois que la cellule doit être mise à jour. Elle permet de mettre à jour l'apparence de la cellule en fonction de son contenu. Si la cellule est vide, le contenu graphique de la cellule est défini sur null, sinon le bouton de suppression est affiché.
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
        id.setCellValueFactory(new PropertyValueFactory<Candidat, Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Candidat, String>("nom"));
        phone.setCellValueFactory(new PropertyValueFactory<Candidat, Integer>("numero_telephone"));
        prenom.setCellValueFactory(new PropertyValueFactory<Candidat, String>("prenom"));

        github.setCellValueFactory(new PropertyValueFactory<Candidat, String>("Github"));

        mail.setCellValueFactory(new PropertyValueFactory<Candidat, String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Candidat, String>("description"));
        education.setCellValueFactory(new PropertyValueFactory<Candidat, String>("education"));
        experience.setCellValueFactory(new PropertyValueFactory<Candidat, String>("experience"));
        ObservableList<Candidat> lu = FXCollections.observableArrayList();
        try {
            lu.addAll(su.getallcandidat());
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setItems(lu);
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Candidat selectedUser = table.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ModifierCandidat.fxml"));
                        Parent root = loader.load();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();

                        ModifierCandidatController controller = loader.getController();
                        controller.initData(selectedUser);
                        controller.setRefreshEvent(this);
                        controller.setPrimarystage(stage);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> Search());
    }

    @FXML
    public void AjouterCandidat(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AjoutCandidat.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        AjoutCandidatController controller = loader.getController();
        controller.setRefreshEvent(this);
        controller.setPrimarystage(stage);
    }

    @FXML
    private void deleteButtonClicked() {

        // Obtenir l'élément sélectionné dans la TableView
        Candidat selectedCandidat = table.getSelectionModel().getSelectedItem();

        if (selectedCandidat != null) {
            // Afficher la fenêtre contextuelle de confirmation
            Alert confirmation = new Alert(AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Supprimer l'élément ?");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet élément ?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Supprimer l'élément de la liste
                    table.getItems().remove(selectedCandidat);
                    su.supprimer(selectedCandidat);
                }
            });
        } else {
            // Aucun élément sélectionné, afficher une alerte d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun élément sélectionné");
            alert.setContentText("Veuillez sélectionner un élément à supprimer.");
            alert.showAndWait();
        }
    }

    public void onRefresh() {
        nom.setCellValueFactory(new PropertyValueFactory<Candidat, String>("nom"));

        prenom.setCellValueFactory(new PropertyValueFactory<Candidat, String>("prenom"));
        phone.setCellValueFactory(new PropertyValueFactory<Candidat, Integer>("numero_telephone"));

        github.setCellValueFactory(new PropertyValueFactory<Candidat, String>("Github"));

        mail.setCellValueFactory(new PropertyValueFactory<Candidat, String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Candidat, String>("description"));
        education.setCellValueFactory(new PropertyValueFactory<Candidat, String>("education"));
        experience.setCellValueFactory(new PropertyValueFactory<Candidat, String>("experience"));
        ObservableList<Candidat> lu = FXCollections.observableArrayList();
        try {
            lu.addAll(su.getallcandidat());
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.setItems(lu);
    }

    
    public void Search() {
        ServiceUser su = new ServiceUser();
        nom.setCellValueFactory(new PropertyValueFactory<Candidat, String>("nom"));

        prenom.setCellValueFactory(new PropertyValueFactory<Candidat, String>("prenom"));
        phone.setCellValueFactory(new PropertyValueFactory<Candidat, Integer>("numero_telephone"));

        github.setCellValueFactory(new PropertyValueFactory<Candidat, String>("Github"));

        mail.setCellValueFactory(new PropertyValueFactory<Candidat, String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Candidat, String>("description"));
        education.setCellValueFactory(new PropertyValueFactory<Candidat, String>("education"));
        experience.setCellValueFactory(new PropertyValueFactory<Candidat, String>("experience"));
        ObservableList<Candidat> lu = FXCollections.observableArrayList();
        try {
            lu.addAll(su.getsearchcandidat(txtSearch.getText()));
        } catch (Exception b) {
            b.getMessage();
        }
        table.setItems(lu);
    }

    @FXML
    public void Retour(ActionEvent e) {
        nom.setCellValueFactory(new PropertyValueFactory<Candidat, String>("nom"));

        prenom.setCellValueFactory(new PropertyValueFactory<Candidat, String>("prenom"));
        phone.setCellValueFactory(new PropertyValueFactory<Candidat, Integer>("numero_telephone"));

        github.setCellValueFactory(new PropertyValueFactory<Candidat, String>("Github"));

        mail.setCellValueFactory(new PropertyValueFactory<Candidat, String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Candidat, String>("description"));
        education.setCellValueFactory(new PropertyValueFactory<Candidat, String>("education"));
        experience.setCellValueFactory(new PropertyValueFactory<Candidat, String>("experience"));
        ObservableList<Candidat> lu = FXCollections.observableArrayList();
        try {
            lu.addAll(su.getallcandidat());
        } catch (Exception z) {
            z.printStackTrace();
        }
        table.setItems(lu);
    }

}
