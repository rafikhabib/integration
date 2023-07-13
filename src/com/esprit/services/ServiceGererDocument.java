/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.esprit.entities.Document;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceGererDocument {

    private Connection cnx = DataSource.GetInstance().getCnx();

    public void ajouter(Document document) {
        try {
            String req = "INSERT INTO document(titre_document, description_document, type, lien, id_user) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, document.getTitre_document());
            pst.setString(2, document.getDescription_document());
            pst.setString(3, document.getType());
            pst.setString(4, document.getLien());
            pst.setInt(5, document.getId_user());
            pst.executeUpdate();
            System.out.println("Document ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Document document) {
        try {
            String req = "UPDATE document SET titre_document=?, description_document=?, type=?, lien=?, id_user=? WHERE id_document=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, document.getTitre_document());
            pst.setString(2, document.getDescription_document());
            pst.setString(3, document.getType());
            pst.setString(4, document.getLien());
            pst.setInt(5, document.getId_user());
            pst.setInt(6, document.getId_document());
            pst.executeUpdate();
            System.out.println("Document modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimer(Document document) {
        try {
            String req = "DELETE FROM document WHERE id_document=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, document.getId_document());
            pst.executeUpdate();
            System.out.println("Document supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Document> afficher() {
        List<Document> list = new ArrayList<>();

        String req = "SELECT * FROM document";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Document(
                        rs.getInt("id_document"),
                        rs.getString("titre_document"),
                        rs.getString("description_document"),
                        rs.getString("type"),
                        rs.getString("lien"),
                        rs.getInt("id_user")
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
}
