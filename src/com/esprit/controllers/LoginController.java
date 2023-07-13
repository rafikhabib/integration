/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.MailException;
import com.esprit.services.ServiceUser;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Checkbox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class LoginController implements Initializable {

    @FXML
    private TextField login;
    @FXML
    private PasswordField motdepasse;
    @FXML
    private TextField afficher;
    @FXML
    private CheckBox showpassword;
    @FXML
    private Button sidentitfier;
    @FXML
    private Button sinscrire;
    @FXML
    private Button motdepasseoublie;
    @FXML
    private FontAwesomeIconView eyeIcon;
    @FXML
    private FontAwesomeIconView eyeIcon1;

    private boolean passwordVisible = false;
    ServiceUser su = new ServiceUser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        eyeIcon.setVisible(true);
        eyeIcon1.setVisible(false);
        motdepasse.setVisible(true);
        afficher.setVisible(false);

    }

    public void showPassword() {

        if (showpassword.isSelected()) {
            afficher.setText(motdepasse.getText());
            afficher.setVisible(true);
            motdepasse.setVisible(false);
        } else {
            motdepasse.setText(afficher.getText());
            afficher.setVisible(false);
            motdepasse.setVisible(true);
        }

    }

    public void togglePasswordVisibility(MouseEvent event) {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            eyeIcon.setVisible(false);
            eyeIcon1.setVisible(true);
            afficher.setText(motdepasse.getText());
            afficher.setVisible(true);
            motdepasse.setVisible(false);
        } else {
            eyeIcon.setVisible(true);
            eyeIcon1.setVisible(false);
            motdepasse.setText(afficher.getText());
            afficher.setVisible(false);
            motdepasse.setVisible(true);
        }
    }

    public void sign(Event e) throws IOException, MailException {
        if (!validateFields()) {
            return;
        }

        ServiceUser su = new ServiceUser();
        String loginText = login.getText();
        String passwordText = motdepasse.getText();
        if (su.login(loginText, passwordText)) {
            if (su.idutilisateur(loginText).equals("Candidat")) {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InterfaceOffreUser.fxml"));

                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage stage = (Stage) login.getScene().getWindow(); // Récupère la fenêtre actuelle
                    stage.setScene(scene); // Définit la nouvelle scène sur la fenêtre
                    InterfaceOffreUserController controller = loader.getController();
                    controller.setIduser(su.getIdBymail(loginText)); //
                    stage.show(); // Affiche la nouvelle scène

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (su.idutilisateur(loginText).equals("Entreprise")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InterfaceOffreEntreprise.fxml"));

                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) login.getScene().getWindow(); // Récupère la fenêtre actuelle
                    stage.setScene(scene); // Définit la nouvelle scène sur la fenêtre
                    InterfaceOffreEntrepriseController controller = loader.getController();
                    controller.setIdEntreprise(su.getIdBymail(loginText));
                    stage.show(); // Affiche la nouvelle scène

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InterfaceUtilisateur.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) login.getScene().getWindow(); // Récupère la fenêtre actuelle
                    stage.setScene(scene); // Définit la nouvelle scène sur la fenêtre

                    InterfaceUtilisateurController controller = loader.getController();
                    controller.setIduser(su.getIdBymail(loginText));

                    stage.show(); // Affiche la nouvelle scène

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Login ou mot de passe erroné !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void oublier(Event e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/motdepasse.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) login.getScene().getWindow(); // Récupère la fenêtre actuelle
            stage.setScene(scene); // Définit la nouvelle scène sur la fenêtre
            stage.show(); // Affiche la nouvelle scène

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public boolean validateFields() {
        if (login.getText().isEmpty() || motdepasse.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Champs obligatoires");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void inscription(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EspaceInscription.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) login.getScene().getWindow(); // Récupère la fenêtre actuelle
        stage.setScene(scene); // Définit la nouvelle scène sur la fenêtre
        stage.show();
    }

}
