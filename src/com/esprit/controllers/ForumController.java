package com.esprit.controllers;

import com.esprit.entities.Commentaire;
import com.esprit.entities.Domaine;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.esprit.entities.Forum;
import com.esprit.services.ServiceCommentaire;
import com.esprit.services.ServiceForum;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;

public class ForumController {

    @FXML
    private ListView<Forum> forumListView;

    @FXML
    private TextField sujetTextField;

    @FXML
    private TextArea contenuTextArea;

    @FXML
    private ChoiceBox<Domaine> domaineChoiceBox;

    private ServiceForum serviceForum;
    private ServiceCommentaire serviceCommentaire;

    private Forum selectedForum;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button createButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;

    @FXML
    private Label forumSujetLabel;

    @FXML
    private Label forumContenuLabel;
    @FXML
    private Button addCommentButton;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private VBox forumInfoBox;
    @FXML
    private VBox commentContainer;
    @FXML
    private TextField chercherTextField;

    private int iduser;

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIduser() {
        return this.iduser;
    }

    public ForumController() {
        serviceForum = new ServiceForum();
        serviceCommentaire = new ServiceCommentaire();
    }

    public void initialize() {
        loadForums();
        loadDomainNames();
        forumInfoBox.setVisible(false);
        forumListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedForum = newValue;
                        try {
                            displayForumDetails(newValue);
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                        forumInfoBox.setVisible(true);
                    } else {
                        forumInfoBox.setVisible(false);
                    }
                }
        );
        chercherTextField.textProperty().addListener((observable, oldValue, newValue) -> chercher());
    }

    @FXML
    private void createForum(ActionEvent event) throws IOException {
        String sujet = sujetTextField.getText();
        String contenu = contenuTextArea.getText();
        Domaine selectedDomaine = domaineChoiceBox.getValue();

        if (sujet.isEmpty() || contenu.isEmpty() || selectedDomaine == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        if (serviceForum.sujetExists(sujet)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Ce Sujet est déja crée !");
            alert.showAndWait();
            return;
        }

        Forum forum = new Forum(sujet, contenu, iduser, selectedDomaine.getId_domaine());
        serviceForum.ajouter(forum);

        loadForums();
        clearFields();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Forum créé avec succès !");
        alert.showAndWait();
    }

    @FXML
    private void deleteForum(ActionEvent event) throws IOException {
        Forum selectedForum = forumListView.getSelectionModel().getSelectedItem();
        if (serviceForum.userCanModifyOrDeleteForum(selectedForum, iduser)) {
            if (selectedForum != null) {

                if (JOptionPane.showConfirmDialog(null, "Voulez vous supprimer ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
                    serviceForum.supprimer(selectedForum);
                    loadForums();
                    clearFields();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Forum supprimé avec succès !");
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un forum !");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Vous n/'etes pas autorisé !");
            alert.showAndWait();
        }
    }

    @FXML
    private void modifyForum(ActionEvent event) throws IOException {
        if (serviceForum.userCanModifyOrDeleteForum(selectedForum, iduser)) {

            if (selectedForum != null) {
                String sujet = sujetTextField.getText();
                String contenu = contenuTextArea.getText();
                Domaine selectedDomaine = domaineChoiceBox.getValue();

                if (sujet.isEmpty() || contenu.isEmpty() || selectedDomaine == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attention");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez remplir tous les champs !");
                    alert.showAndWait();
                    return;
                }
                selectedForum.setSujet(sujet);
                selectedForum.setContenu(contenu);
                selectedForum.setId_domaine(selectedDomaine.getId_domaine());

                serviceForum.modifier(selectedForum);

                loadForums();
                clearFields();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Forum modifié avec succès !");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un forum !");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Vous n/'etes pas autorisé !");
            alert.showAndWait();
        }

    }

    private void loadForums() {
        forumListView.getItems().clear();
        List<Forum> forums = serviceForum.afficher();
        for (Forum forum : forums) {
            forumListView.getItems().add(forum);
        }

        forumListView.setCellFactory(listView -> new ListCell<Forum>() {
            @Override
            protected void updateItem(Forum forum, boolean empty) {
                super.updateItem(forum, empty);
                if (empty || forum == null) {
                    setText(null);
                } else {
                    setText("Sujet: " + forum.getSujet() + ", Contenu: " + forum.getContenu()
                            + ", Utilisateur: " + serviceForum.getUserNameById(forum.getId_user()) + ",Domaine: " + serviceForum.getDomaineNameById(forum.getId_domaine()));
                }
            }
        });
    }

    private void loadDomainNames() {
        List<Domaine> domaines = serviceForum.getDomaines();

        ObservableList<Domaine> domaineList = FXCollections.observableArrayList(domaines);
        domaineChoiceBox.setItems(domaineList);
        domaineChoiceBox.setConverter(new StringConverter<Domaine>() {
            @Override
            public String toString(Domaine domaine) {
                return domaine.getNom_domaine();
            }

            @Override
            public Domaine fromString(String string) {
                return null;
            }
        });
    }

    private void displayForumDetails(Forum forum) throws SQLException {
        sujetTextField.setText(forum.getSujet());
        contenuTextArea.setText(forum.getContenu());
        domaineChoiceBox.setValue(serviceForum.getDomainById(forum.getId_domaine()));

        forumSujetLabel.setText(forum.getSujet());
        forumContenuLabel.setText(forum.getContenu());

        loadComments();
    }

    private void clearFields() {
        sujetTextField.clear();
        contenuTextArea.clear();
        domaineChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void addComment(ActionEvent event) throws IOException, SQLException {
        String commentText = commentTextArea.getText();
        if (commentText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir commentaire !");
            alert.showAndWait();
            return;
        }

        Commentaire commentaire = new Commentaire(commentText, selectedForum.getId_forum(), iduser);
        serviceCommentaire.ajouter(commentaire);

        commentTextArea.clear();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Commentaire créé avec succès !");
        alert.showAndWait();
        loadComments();
    }

    void loadComments() throws SQLException {
        commentContainer.getChildren().clear();

        List<Commentaire> commentaires = serviceCommentaire.getCommentsForForum(selectedForum.getId_forum());
        for (Commentaire commentaire : commentaires) {
            CommentaireController commentaireController = new CommentaireController(commentaire, this);
            commentaireController.setForumController(this);
            VBox commentComponent = commentaireController.getCommentComponent();
            commentContainer.getChildren().add(commentComponent);
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        chercherTextField.setText("");
        loadForums();
    }

    private void chercher() {
        forumListView.getItems().clear();
        List<Forum> forums = serviceForum.rechercher(chercherTextField.getText());
        for (Forum forum : forums) {
            forumListView.getItems().add(forum);
        }

        forumListView.setCellFactory(listView -> new ListCell<Forum>() {
            @Override
            protected void updateItem(Forum forum, boolean empty) {
                super.updateItem(forum, empty);
                if (empty || forum == null) {
                    setText(null);
                } else {
                    setText("Sujet: " + forum.getSujet() + ", Contenu: " + forum.getContenu()
                            + ", Utilisateur: " + serviceForum.getUserNameById(forum.getId_user()) + ",Domaine: " + serviceForum.getDomaineNameById(forum.getId_domaine()));
                }
            }
        });
    }
}
