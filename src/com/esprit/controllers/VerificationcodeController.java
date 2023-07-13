/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.controllers.ChangermotdepasseController;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Anis
 */
public class VerificationcodeController implements Initializable {

    @FXML
    private Button submit;
    @FXML
    private Label txt1;
    @FXML
    private Label txt2;
    @FXML
    private Label txt3;
    @FXML
    private TextField tfcode;
    private String generatecode;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setGeneratecode(String generatecode) {
        this.generatecode = generatecode;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt1.setWrapText(true);
        txt2.setWrapText(true);
        txt3.setWrapText(true);
        // TODO
    }  
    public void verifcode(ActionEvent e) throws IOException{
        String cd = tfcode.getText();
        if(cd.equals(generatecode)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/changermotdepasse.fxml"));
            Parent root = loader.load();
            ChangermotdepasseController cm = loader.getController();
            cm.setId(id);
            Scene scene = new Scene(root);
            Stage stage = (Stage) txt1.getScene().getWindow(); // Récupère la fenêtre actuelle
            stage.setScene(scene); // Définit la nouvelle scène sur la fenêtre
            stage.show(); // Affiche la nouvelle scène
            JOptionPane.showMessageDialog(null, "code correct !");
        }
        else{
            JOptionPane.showMessageDialog(null, "code erroné !","Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    
    
}
