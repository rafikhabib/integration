/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.Administrateur;
import com.esprit.services.ServiceUser;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class AdminController implements Initializable, Refresh {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Administrateur> table;
    @FXML
    private TableColumn<Administrateur, Integer> id;
    @FXML
    private TableColumn<Administrateur, String> nom;
    @FXML
    private TableColumn<Administrateur, String> prenom;
    @FXML
    private TableColumn<Administrateur, String> mail;
    @FXML
    private TableColumn<Administrateur, Integer> telephone;
    @FXML
    private TableColumn<Administrateur, String> description;
    @FXML
    private TableColumn<Administrateur, Void> supprimer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceUser su = new ServiceUser();
        id.setCellValueFactory(new PropertyValueFactory<Administrateur, Integer>("id"));
        id.setVisible(false);
        nom.setCellValueFactory(new PropertyValueFactory<Administrateur, String>("nom"));
        telephone.setCellValueFactory(new PropertyValueFactory<Administrateur, Integer>("numero_telephone"));
        prenom.setCellValueFactory(new PropertyValueFactory<Administrateur, String>("prenom"));

        mail.setCellValueFactory(new PropertyValueFactory<Administrateur, String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Administrateur, String>("description"));
        ObservableList<Administrateur> lu = FXCollections.observableArrayList();
        try {
            lu.addAll(su.getalladmint());
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setItems(lu);
        supprimer.setCellFactory(column -> {
            TableCell<Administrateur, Void> cell = new TableCell<Administrateur, Void>() {
                //La cellule contient un bouton de suppression déclaré et initialisé en tant que Button avec le texte "Supprimer".
                private final Button deleteButton = new Button("Supprimer");

                {
                    //Un gestionnaire d'événements est défini sur le bouton en utilisant setOnAction. Lorsque le bouton est cliqué, le code à l'intérieur du bloc est exécuté.
                    deleteButton.setOnAction(event -> {
                        //Cette ligne récupère l'élément (Entreprisedomaine) correspondant à la ligne de la cellule actuelle. getTableView() renvoie la référence de la table associée à la cellule, et getItems() renvoie la liste des éléments affichés dans la table. getIndex() renvoie l'index de la ligne de la cellule actuelle.
                        Administrateur Admin = getTableView().getItems().get(getIndex());
                        if (Admin != null) {
                            // Afficher la fenêtre contextuelle de confirmation
                            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmation.setTitle("Confirmation");
                            confirmation.setHeaderText("Supprimer l'admin ?");
                            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet admin ?");
                            //Cette ligne affiche la boîte de dialogue de confirmation et attend que l'utilisateur fasse un choix. La méthode showAndWait() suspend l'exécution jusqu'à ce que l'utilisateur ferme la boîte de dialogue.
                            //L'interface Optional<T> est une fonctionnalité introduite à partir de Java 8 qui permet de représenter une valeur optionnelle qui peut être présente ou absente. Dans le contexte du code que vous avez partagé, Optional<ButtonType> est utilisé pour représenter le résultat de la boîte de dialogue de confirmation.
                            //La méthode showAndWait() de la classe Alert renvoie un objet Optional<ButtonType>. Cet objet Optional contient le résultat de la boîte de dialogue (le bouton sur lequel l'utilisateur a cliqué) s'il est présent, sinon il est vide.
                            Optional<ButtonType> result = confirmation.showAndWait();
                            //Cette partie du code vérifie si l'utilisateur a fait un choix et si le choix est égal à ButtonType.OK (le bouton OK de la boîte de dialogue de confirmation)
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                // Supprimer le candidat de la liste
                                table.getItems().remove(Admin);
                                su.supprimer(Admin);
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

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Administrateur selectedUser = table.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ModifierAdmin.fxml"));
                        Parent root = loader.load();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();

                        ModifierAdminController controller = loader.getController();
                        controller.initData(selectedUser);
                        controller.setRefreshEvent(this);
                        controller.setPrimarystage(stage);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }
    
    @FXML
    public void AjouterAdmin(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AjoutAdmin.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        AjoutAdminController controller = loader.getController();
        controller.setRefreshEvent(this);
        controller.setPrimarystage(stage);
    }

    public void onRefresh() {
        ServiceUser su = new ServiceUser();
        id.setCellValueFactory(new PropertyValueFactory<Administrateur, Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Administrateur, String>("nom"));
        telephone.setCellValueFactory(new PropertyValueFactory<Administrateur, Integer>("numero_telephone"));
        prenom.setCellValueFactory(new PropertyValueFactory<Administrateur, String>("prenom"));

        mail.setCellValueFactory(new PropertyValueFactory<Administrateur, String>("mail"));
        description.setCellValueFactory(new PropertyValueFactory<Administrateur, String>("description"));
        ObservableList<Administrateur> lu = FXCollections.observableArrayList();
        try {
            lu.addAll(su.getalladmint());
        } catch (Exception b) {
            b.printStackTrace();
        }
        table.setItems(lu);
    }

}
