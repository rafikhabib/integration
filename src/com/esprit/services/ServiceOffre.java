/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.esprit.entities.Offre;
import com.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ASUS
 */
public class ServiceOffre implements IServices<Offre> {

    private Connection cnx = DataSource.GetInstance().getCnx();

    public class OffreView {

        int id_offre;
        int id_entreprise;
        String NomEntreprise;
        String titre;
        String description;
        int id_domaine;
        String nomDomaine;
        Date date_pub;
        Date date_Exp;

        public OffreView(int id_offre, int id_entreprise, String nomEntreprise, String titre, String description, int id_domaine, String nomDomaine, Date date_pub, Date date_Exp) {
            this.id_offre = id_offre;
            this.id_entreprise = id_entreprise;
            this.NomEntreprise = nomEntreprise;
            this.titre = titre;
            this.description = description;
            this.id_domaine = id_domaine;
            this.nomDomaine = nomDomaine;
            this.date_pub = date_pub;
            this.date_Exp = date_Exp;
        }

        @Override
        public String toString() {
            return "OffreView{" + "id_offre=" + id_offre + ", id_entreprise=" + id_entreprise + ", nomEntreprise=" + NomEntreprise + ", titre=" + titre + ", description=" + description + ", id_domaine=" + id_domaine + ", nomDomaine=" + nomDomaine + ", date_pub=" + date_pub + ", date_Exp=" + date_Exp + '}';
        }

        public int getId_offre() {
            return id_offre;
        }

        public void setId_offre(int id_offre) {
            this.id_offre = id_offre;
        }

        public int getId_entreprise() {
            return id_entreprise;
        }

        public void setId_entreprise(int id_entreprise) {
            this.id_entreprise = id_entreprise;
        }

        public String getNomEntreprise() {
            return NomEntreprise;
        }

        public void setNomEntreprise(String nomEntreprise) {
            this.NomEntreprise = nomEntreprise;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId_domaine() {
            return id_domaine;
        }

        public void setId_domaine(int id_domaine) {
            this.id_domaine = id_domaine;
        }

        public String getNomDomaine() {
            return nomDomaine;
        }

        public void setNomDomaine(String nomDomaine) {
            this.nomDomaine = nomDomaine;
        }

        public Date getDate_pub() {
            return date_pub;
        }

        public void setDate_pub(Date date_pub) {
            this.date_pub = date_pub;
        }

        public Date getDate_Exp() {
            return date_Exp;
        }

        public void setDate_Exp(Date date_Exp) {
            this.date_Exp = date_Exp;
        }

    }

    @Override
    public void ajouter(Offre f) {
        String req = "INSERT INTO offre(titre,description,id_domaine,id_entreprise,date_expiration) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);

            pst.setString(1, f.getTitre());
            pst.setString(2, f.getDescription());
            pst.setInt(3, f.getId_domaine());
            pst.setInt(4, f.getId_entreprise());
            pst.setDate(5, f.getDate_Expiration());
            pst.executeUpdate();
            System.out.println("ajouter avec Succes !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Offre f) {
        String req = "UPDATE offre SET titre = ? , description = ? , id_domaine = ? ,id_entreprise = ?, date_offre = ? , date_expiration = ? WHERE id_offre = ? ";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);

            pst.setString(1, f.getTitre());
            pst.setString(2, f.getDescription());
            pst.setInt(3, f.getId_domaine());
            pst.setInt(4, f.getId_entreprise());
            pst.setDate(5, f.getDate_offre());
            pst.setDate(6, f.getDate_Expiration());
            pst.setInt(7, f.getId_offre());

            pst.executeUpdate();
            System.out.println("modifier avec Succes !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Offre f) {
        String req = "DELETE FROM offre WHERE id_offre = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, f.getId_offre());
            pst.executeUpdate();
            System.out.println("Supprimer avec Succes !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerParId(int id) {
        String req = "DELETE FROM offre WHERE id_offre = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Supprimer avec Succes !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Offre> afficher() {
        List<Offre> list = new ArrayList<>();
        String req = "SELECT * FROM offre";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                list.add(new Offre(res.getInt("id_offre"), res.getString("titre"), res.getString("description"), res.getInt("id_domaine"), res.getInt("id_entreprise"), res.getDate("date_offre"), res.getDate("date_expiration")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    /**
     * ***************  ****************************
     */
    public List<OffreView> afficherOffres() {
        List<OffreView> list = new ArrayList<>();
        String req = "SELECT f.id_offre ,f.titre,f.description,f.date_offre,f.date_expiration,d.id_domaine,d.nom_domaine,u.id,u.NomEntreprise FROM domaine d join offre f join user u on d.id_domaine = f.id_domaine and f.id_entreprise = u.id ";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                list.add(new OffreView(res.getInt("id_offre"), res.getInt("id"), res.getString("NomEntreprise"), res.getString("titre"), res.getString("description"), res.getInt("id_domaine"), res.getString("nom_domaine"), Date.valueOf(res.getString("date_offre")), Date.valueOf(res.getString("date_expiration"))));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(list.toString());
        return list;

    }

    public List<OffreView> afficherOffresByEntreprise(int idE) {
        List<OffreView> list = new ArrayList<>();
        String req = "SELECT f.id_offre ,f.titre,f.description,f.date_offre,f.date_expiration,d.id_domaine,d.nom_domaine,u.id,u.NomEntreprise FROM domaine d join offre f join user u on d.id_domaine = f.id_domaine and f.id_entreprise = u.id Where f.id_entreprise = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, idE);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                list.add(new OffreView(res.getInt("id_offre"), res.getInt("id"), res.getString("NomEntreprise"), res.getString("titre"), res.getString("description"), res.getInt("id_domaine"), res.getString("nom_domaine"), Date.valueOf(res.getString("date_offre")), Date.valueOf(res.getString("date_expiration"))));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public List<OffreView> afficherOffresByEntreprisefiltree(int idE, String domaine) {
        List<OffreView> list = new ArrayList<>();
        String req = "SELECT f.id_offre ,f.titre,f.description,f.date_offre,f.date_expiration,d.id_domaine,d.nom_domaine,u.id,u.NomEntreprise FROM domaine d join offre f join user u on d.id_domaine = f.id_domaine and f.id_entreprise = u.id Where f.id_entreprise = ? and d.nom_domaine = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, idE);
            pst.setString(2, domaine);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                list.add(new OffreView(res.getInt("id_offre"), res.getInt("id"), res.getString("NomEntreprise"), res.getString("titre"), res.getString("description"), res.getInt("id_domaine"), res.getString("nom_domaine"), Date.valueOf(res.getString("date_offre")), Date.valueOf(res.getString("date_expiration"))));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public List<OffreView> afficherOffresByDomaine(String Domaine) {
        List<OffreView> list = new ArrayList<>();
        String req = "SELECT f.id_offre ,f.titre,f.description,f.date_offre,f.date_expiration,d.id_domaine,d.nom_domaine,u.id,u.NomEntreprise FROM domaine d join offre f join user u on d.id_domaine = f.id_domaine and f.id_entreprise = u.id Where d.nom_domaine = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, Domaine);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                list.add(new OffreView(res.getInt("id_offre"), res.getInt("id"), res.getString("NomEntreprise"), res.getString("titre"), res.getString("description"), res.getInt("id_domaine"), res.getString("nom_domaine"), Date.valueOf(res.getString("date_offre")), Date.valueOf(res.getString("date_expiration"))));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public Offre chercherOffreByID(int id) {
        Offre off = null;
        String req = "SELECT * FROM offre WHERE id_offre = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                off = new Offre(res.getInt("id_offre"), res.getString("titre"), res.getString("description"), res.getInt("id_domaine"), res.getInt("id_entreprise"), res.getDate("date_offre"), res.getDate("date_expiration"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return off;

    }

    public ObservableList<Offre> getObsList() {
//        List<Offre> list = new ArrayList<>();
        ObservableList<Offre> list = FXCollections.observableArrayList();
        String req = "SELECT * FROM offre";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                list.add(new Offre(res.getInt("id_offre"), res.getString("titre"), res.getString("description"), res.getInt("id_domaine"), res.getInt("id_entreprise"), res.getDate("date_offre"), res.getDate("date_expiration")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

}
