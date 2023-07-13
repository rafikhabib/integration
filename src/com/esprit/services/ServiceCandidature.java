/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.esprit.entities.Candidature;
import com.esprit.entities.EtatCandidature;
import com.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ServiceCandidature implements IServices<Candidature> {

    private Connection cnx = DataSource.GetInstance().getCnx();

    @Override
    public void ajouter(Candidature c) {
        try {
            String req = "INSERT INTO candidature"
                    + " (id_user,id_offre)"
                    + " VALUES (?,?)";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, c.getId_user());
            pst.setInt(2, c.getId_offre());

            pst.executeUpdate();
            System.out.println("ajout avec success !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Candidature c) {
        try {
            String req = "UPDATE candidature SET date_candidature = ?  , etat = ? "
                    + "WHERE id_user= ? and id_offre = ? and id_candidature =?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setDate(1, c.getDate_condidature());
            pst.setString(2, c.getEtat().toString());
            pst.setInt(3, c.getId_user());
            pst.setInt(4, c.getId_offre());
            pst.setInt(5, c.getId_candidature());

            pst.executeUpdate();
            System.out.println("modifier avec success !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Candidature c) {
        try {
            String req = "DELETE FROM candidature WHERE "
                    + "id_user= ? and id_offre = ? and id_candidature =?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, c.getId_user());
            pst.setInt(2, c.getId_offre());
            pst.setInt(3, c.getId_candidature());
            pst.executeUpdate();
            System.out.println("supprimer avec success !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Candidature> afficher() {
        List<Candidature> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM candidature";
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                list.add(new Candidature(res.getInt("id_user"), res.getInt("id_offre"), res.getInt("id_candidature"), res.getDate("date_candidature"), EtatCandidature.valueOf(res.getString("etat"))));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public class CandidatureDetails {

        private int id_candidature;
        private Date date_condidature;
        private EtatCandidature etat;
        private int id_user;
        private String nom_user;
        private String prenom_user;
        private int id_offre;
        private String titreoffre;
        private String nom_entreprise;
        private String nom_domaine;

        public CandidatureDetails(int id_candidature, Date date_condidature, EtatCandidature etat, int id_user, String nom_user, String prenom_user, int id_offre, String titreoffre, String nom_entreprise, String nom_domaine) {
            this.id_candidature = id_candidature;
            this.date_condidature = date_condidature;
            this.etat = etat;
            this.id_user = id_user;
            this.nom_user = nom_user;
            this.prenom_user = prenom_user;
            this.id_offre = id_offre;
            this.titreoffre = titreoffre;
            this.nom_entreprise = nom_entreprise;
            this.nom_domaine = nom_domaine;
        }

        public int getId_candidature() {
            return id_candidature;
        }

        public void setId_candidature(int id_candidature) {
            this.id_candidature = id_candidature;
        }

        public Date getDate_condidature() {
            return date_condidature;
        }

        public void setDate_condidature(Date date_condidature) {
            this.date_condidature = date_condidature;
        }

        public EtatCandidature getEtat() {
            return etat;
        }

        public void setEtat(EtatCandidature etat) {
            this.etat = etat;
        }

        public int getId_user() {
            return id_user;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public String getNom_user() {
            return nom_user;
        }

        public void setNom_user(String nom_user) {
            this.nom_user = nom_user;
        }

        public String getPrenom_user() {
            return prenom_user;
        }

        public void setPrenom_user(String prenom_user) {
            this.prenom_user = prenom_user;
        }

        public int getId_offre() {
            return id_offre;
        }

        public void setId_offre(int id_offre) {
            this.id_offre = id_offre;
        }

        public String getTitreoffre() {
            return titreoffre;
        }

        public void setTitreoffre(String titreoffre) {
            this.titreoffre = titreoffre;
        }

        public String getNom_entreprise() {
            return nom_entreprise;
        }

        public void setNom_entreprise(String nom_entreprise) {
            this.nom_entreprise = nom_entreprise;
        }

        public String getNom_domaine() {
            return nom_domaine;
        }

        public void setNom_domaine(String nom_domaine) {
            this.nom_domaine = nom_domaine;
        }

        @Override
        public String toString() {
            return "CandidatureDetails{" + "id_candidature=" + id_candidature + ", date_condidature=" + date_condidature + ", etat=" + etat + ", id_user=" + id_user + ", nom_user=" + nom_user + ", prenom_user=" + prenom_user + ", id_offre=" + id_offre + ", titreoffre=" + titreoffre + ", nom_entreprise=" + nom_entreprise + ", nom_domaine=" + nom_domaine + '}';
        }

    }

    public List<CandidatureDetails> candidatureDetails() {
        List<CandidatureDetails> list = new ArrayList<>();
        try {
            String req = "SELECT c.id_candidature ,c.date_candidature,c.etat,c.id_user ,(SELECT nom from user u WHERE u.id = c.id_user) as nom,(SELECT prenom from user u WHERE u.id = c.id_user) as prenom ,c.id_offre,(SELECT titre FROM offre o WHERE o.id_offre =c.id_offre ) AS titre,(SELECT nom from user where id = (SELECT id_entreprise from offre WHERE id_offre = c.id_offre)) as NomEntreprise,(SELECT nom_domaine from domaine d where d.id_domaine = (SELECT id_domaine from offre WHERE id_offre = c.id_offre)) AS nom_domaine from candidature c;";
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                list.add(new CandidatureDetails(res.getInt("id_candidature"), Date.valueOf(res.getString("date_candidature")), EtatCandidature.valueOf(res.getString("etat")), res.getInt("id_user"), res.getString("nom"), res.getString("prenom"), res.getInt("id_offre"), res.getString("titre"), res.getString("NomEntreprise"), res.getString("nom_domaine")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public List<CandidatureDetails> candidatureDetailsParEntreprise(int ide) {
        List<CandidatureDetails> list = new ArrayList<>();
        try {
            String req = "SELECT c.id_candidature ,c.date_candidature,c.etat,c.id_user ,(SELECT nom from user u WHERE u.id = c.id_user) as nom,(SELECT prenom from user u WHERE u.id = c.id_user) as prenom ,c.id_offre,(SELECT titre FROM offre o WHERE o.id_offre =c.id_offre ) AS titre,(SELECT nom from user where id = (SELECT id_entreprise from offre WHERE id_offre = c.id_offre)) as NomEntreprise,(SELECT nom_domaine from domaine d where d.id_domaine = (SELECT id_domaine from offre WHERE id_offre = c.id_offre)) AS nom_domaine from candidature c where (SELECT id_entreprise from offre WHERE id_offre = c.id_offre) = ?;";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, ide);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                list.add(new CandidatureDetails(res.getInt("id_candidature"), Date.valueOf(res.getString("date_candidature")), EtatCandidature.valueOf(res.getString("etat")), res.getInt("id_user"), res.getString("nom"), res.getString("prenom"), res.getInt("id_offre"), res.getString("titre"), res.getString("NomEntreprise"), res.getString("nom_domaine")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public boolean checkCandidature(int idu, int ido) {
        Candidature can = null;
        try {
            String req = "select id_user,id_offre from candidature where id_user = ? and  id_offre = ?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, idu);
            pst.setInt(2, ido);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                can = new Candidature(res.getInt("id_user"), res.getInt("id_offre"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        if (can == null) {
            return true;
        } else {
            return false;
        }
    }

}
