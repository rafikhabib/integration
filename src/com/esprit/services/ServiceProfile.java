package com.esprit.services;

import com.esprit.entities.Profile;
import com.esprit.entities.Profile.Niveau;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProfile {

    private Connection cnx = DataSource.GetInstance().getCnx();

    public void ajouter(Profile profile) {
        try {
            String req = "INSERT INTO profiles(id_user, id_competence, niveau) VALUES (?, ?, ?);";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, profile.getId_user());
            pst.setInt(2, profile.getId_competence());
            pst.setString(3, profile.getNiveau().toString());
            pst.executeUpdate();
            System.out.println("Profile ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Profile profile) {
        try {
            String req = "UPDATE profiles SET id_user=?, id_competence=?, niveau=? WHERE id_profile=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, profile.getId_user());
            pst.setInt(2, profile.getId_competence());
            pst.setString(3, profile.getNiveau().toString());
            pst.setInt(4, profile.getId_profile());
            pst.executeUpdate();
            System.out.println("Profile modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimer(Profile profile) {
        try {
            String req = "DELETE FROM profiles WHERE id_profile=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, profile.getId_profile());
            pst.executeUpdate();
            System.out.println("Profile supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Profile> afficher() {
        List<Profile> list = new ArrayList<>();

        String req = "SELECT * FROM profiles";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Profile(
                        rs.getInt("id_profile"),
                        rs.getInt("id_user"),
                        rs.getInt("id_competence"),
                        Niveau.valueOf(rs.getString("niveau"))
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
}
