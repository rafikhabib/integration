/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.*;
import com.esprit.services.ServiceMail;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class MotdepasseController implements Initializable {

    @FXML
    private TextField login;
    @FXML
    private Button submit;
    @FXML
    private Button retour;
    @FXML
    private Label erreur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void login(Event e) throws MessagingException {

        ServiceUser su = new ServiceUser();
        ServiceMail sm = new ServiceMail();
        String loginText = login.getText();
        if (su.loginpasse(loginText)) {
            try {
                String code = CodeGenerator.generateCode();
                String subject = "Code de vérification";
                String body = "Bonjour,\n" +
                "\n" +
                "Nous avons reçu une demande de réinitialisation du mot de passe de votre compte Findjob.\n" +
                "\n" +
                code + "\n" +
                "Veuillez saisir ce code pour finaliser la réinitialisation.\n" +
                "\n" +
                "Merci de nous aider à maintenir la sécurité de votre compte.\n" +
                "\n" +
                "L'équipe Findjob\"";
                sm.sendMail(loginText, subject, body);
                JOptionPane.showMessageDialog(null, code);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/verificationcode.fxml"));
                Parent root = loader.load();
                VerificationcodeController vc = loader.getController();
                vc.setGeneratecode(code);
                vc.setId(loginText);
                Scene scene = new Scene(root);
                Stage stage = (Stage) login.getScene().getWindow(); // Récupère la fenêtre actuelle
                stage.setScene(scene); // Définit la nouvelle scène sur la fenêtre
                stage.show(); // Affiche la nouvelle scène

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            erreur.setText("Nous n'avons trouvé aucun compte associé à" + " " + login.getText() + " " + "Veuillez essayer avec une adresse e-mail ou un numéro de téléphone alternatif.");
            erreur.setWrapText(true);
        }

    }

    public void retour(Event e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/login1.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) login.getScene().getWindow(); // Récupère la fenêtre actuelle
            stage.setScene(scene); // Définit la nouvelle scène sur la fenêtre
            stage.show(); // Affiche la nouvelle scène
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
