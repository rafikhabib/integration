package com.esprit.controllers;

import com.esprit.entities.Profile;
import com.esprit.services.ServiceProfile;
import com.esprit.services.ServiceCompetence;
import com.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private ComboBox<String> userNameComboBox;

    @FXML
    private ComboBox<String> competenceComboBox;

    @FXML
    private ComboBox<Profile.Niveau> niveauComboBox;

    private final ServiceProfile serviceProfile;
    private final ServiceCompetence serviceCompetence;

    public ProfileController() {
        serviceProfile = new ServiceProfile();
        serviceCompetence = new ServiceCompetence();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate the user name combo box
        populateUserNameComboBox();

        // Set up the competence combo box
        setupCompetenceComboBox();

        // Populate the niveau combo box
        populateNiveauComboBox();
    }

    private void populateUserNameComboBox() {
        try {
            // Fetch user names from the database and populate the combo box
            Connection cnx = DataSource.GetInstance().getCnx();
            String req = "SELECT nom FROM user";
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();

            List<String> userNames = new ArrayList<>();
            while (rs.next()) {
                String userName = rs.getString("nom");
                userNames.add(userName);
            }

            userNameComboBox.setItems(FXCollections.observableArrayList(userNames));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setupCompetenceComboBox() {
        // Disable the competence combo box initially
        competenceComboBox.setDisable(true);

        // Listen for changes in the selected user name
        userNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Fetch competence names from the database based on the selected user name
                List<String> competenceNames = serviceCompetence.getCompetenceNamesByUserName(newValue);

                if (competenceNames.isEmpty()) {
                    competenceComboBox.setItems(FXCollections.emptyObservableList());
                    competenceComboBox.setDisable(true);
                } else {
                    competenceComboBox.setItems(FXCollections.observableArrayList(competenceNames));
                    competenceComboBox.setDisable(false);
                }
            } else {
                // Clear the competence combo box if no user name is selected
                competenceComboBox.getItems().clear();
                competenceComboBox.setDisable(true);
            }
        });
    }

    private void populateNiveauComboBox() {
        // Populate the niveau combo box
        niveauComboBox.getItems().addAll(Profile.Niveau.values());
    }

    @FXML
    private void addProfile() {
        // Get the selected values from the combo boxes
        String userName = userNameComboBox.getValue();
        String competenceName = competenceComboBox.getValue();
        Profile.Niveau niveau = niveauComboBox.getValue();

        // Fetch the competence ID based on the selected competence name
        int competenceId = serviceCompetence.getCompetenceIdByName(competenceName);

        // Create a new Profile object with the selected values
        Profile profile = new Profile();
        profile.setId_user(getUserIdFromUserName(userName));
        profile.setId_competence(competenceId);
        profile.setNiveau(niveau);

        // Call the service to add the profile
        serviceProfile.ajouter(profile);
    }

    private int getUserIdFromUserName(String userName) {
        try {
            // Retrieve the user ID from the database based on the user name
            Connection cnx = DataSource.GetInstance().getCnx();
            String req = "SELECT id FROM user WHERE nom = ?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0;
    }
}
