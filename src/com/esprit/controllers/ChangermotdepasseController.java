/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class ChangermotdepasseController implements Initializable {
    
    @FXML
    private PasswordField motdepasse;
    @FXML
    private PasswordField motdepasse2;
    @FXML
    private Button submit;
    private String id;
    
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void changermotdepasse(ActionEvent e) throws IOException {
        if (motdepasse.getText().equals(motdepasse2.getText())) {
            ServiceUser su = new ServiceUser();
            su.modifiermotdepasse(motdepasse.getText(),id);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/login1.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) motdepasse.getScene().getWindow(); // Récupère la fenêtre actuelle
            stage.setScene(scene); // Définit la nouvelle scène sur la fenêtre
            stage.show(); // Affiche la nouvelle scène
            JOptionPane.showMessageDialog(null, "mot de passe correct !");
        } else {
            JOptionPane.showMessageDialog(null, "mot de passe erroné !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}


