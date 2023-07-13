package com.esprit.main;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
import com.esprit.controllers.AjoutCandidatController;
import com.esprit.controllers.AjoutEntrepriseController;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Anis
 */
public class MainprogGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AjoutCandidat.fxml"));

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AjoutEntreprise.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AjoutAdmin.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Admin.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/User.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Candidat.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Entreprise.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login1.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GestionUtilisateur.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/motdepasse.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/verificationcode.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Forum.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setTitle("FindJob");

        primaryStage.show();
//        AjoutCandidatController candidatcontroller = loader.getController();
//        candidatcontroller.setPrimarystage(primaryStage);
//        AjoutEntrepriseController entreprisecontroller = loader.getController();
//        entreprisecontroller.setPrimarystage(primaryStage);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
