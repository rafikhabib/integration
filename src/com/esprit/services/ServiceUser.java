/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.esprit.utils.DataSource;
import java.sql.Connection;
import com.esprit.entities.*;
import com.mysql.cj.conf.PropertyKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Anis
 */
public class ServiceUser {

    private Connection cnx = DataSource.GetInstance().getCnx();

    public void ajouter(User p) throws MailException {
        if (p instanceof Candidat) {
            try {
                int id_user = 0;
                String req = "insert into user (nom,prenom,mail,numero_telephone,motdepasse,description,education,role, Github, experience) values (?,?,?,?,?,?,?,?,?,?);";
                String req1 = "insert into profil (id_user,id_c) values (?,?);";
                String req2 = "select last_insert_id() from user;";
                PreparedStatement pst = cnx.prepareStatement(req);
                PreparedStatement pst1 = cnx.prepareStatement(req1);
                PreparedStatement pst2 = cnx.prepareStatement(req2);
                pst.setString(1, p.getNom());
                pst.setString(2, p.getPrenom());
                pst.setString(3, p.getMail());
                pst.setInt(4, p.getNumero_telephone());
                pst.setString(5, p.getMotdepasse());
                pst.setString(6, p.getDescription());
                pst.setString(7, ((Candidat) p).getEducation().name());
                pst.setString(8, p.getClass().getSimpleName());
                pst.setString(9, ((Candidat) p).getGithub());
                pst.setString(10, ((Candidat) p).getExperience().name());
                List<Candidat> list = afficherCandidat();
                Boolean candidatexiste = false;
                for (Candidat u : list) {
                    if (u.getMail().equals(p.getMail()) || u.getNumero_telephone() == p.getNumero_telephone()) {
                        candidatexiste = true;
                        break;
                    }
                }
                if (candidatexiste) {
                    throw new MailException("candidat existe deja");
                }
                pst.executeUpdate();

                ResultSet rs = pst2.executeQuery();
                while (rs.next()) {
                    id_user = rs.getInt("last_insert_id()");
                }
                for (int id : ((Candidat) p).getListeCompetences()) {
                    pst1.setInt(1, id_user);
                    pst1.setInt(2, id);
                    pst1.executeUpdate();
                }

                System.out.println("Candidat ajouté");
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        } else if (p instanceof Entreprise) {
            try {
                String req = "insert into user (nom,prenom,mail,numero_telephone,motdepasse,description,NomEntreprise,role,TailleEntreprise,SiteWeb,Linkedin,id_domaine) values (?,?,?,?,?,?,?,?,?,?,?,?);";
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setString(1, p.getNom());
                pst.setString(2, p.getPrenom());
                pst.setString(3, p.getMail());
                pst.setInt(4, p.getNumero_telephone());
                pst.setString(5, p.getMotdepasse());
                pst.setString(6, p.getDescription());
                pst.setString(7, ((Entreprise) p).getNomEntreprise());
                pst.setString(8, p.getClass().getSimpleName());
                pst.setString(9, ((Entreprise) p).getTailleEntreprise().name());
                pst.setString(10, ((Entreprise) p).getSiteWeb());
                pst.setString(11, ((Entreprise) p).getLinkedin());
                pst.setInt(12, ((Entreprise) p).getId_domaine());
                List<Entreprisedomaine> list = afficherentreprise();
                Boolean entreprisetexiste = false;
                for (Entreprisedomaine u : list) {
                    if (u.getMail().equals(p.getMail()) || u.getNumero_telephone() == p.getNumero_telephone()) {
                        entreprisetexiste = true;
                        break;
                    }
                }
                if (entreprisetexiste) {
                    throw new MailException("Entreprise existe deja");
                }
                pst.executeUpdate();
                System.out.println("Entreprise ajoutée");
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        } else {
            try {
                String req = "insert into user (nom,prenom,mail,numero_telephone,motdepasse,description,role) values (?,?,?,?,?,?,?);";
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setString(1, p.getNom());
                pst.setString(2, p.getPrenom());
                pst.setString(3, p.getMail());
                pst.setInt(4, p.getNumero_telephone());
                pst.setString(5, p.getMotdepasse());
                pst.setString(6, p.getDescription());
                pst.setString(7, p.getClass().getSimpleName());

                pst.executeUpdate();
                System.out.println("Admin ajouté");
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        }
    }

    public void modifier(User p) throws MailException {
        if (p instanceof Candidat) {

            try {
                String req = "UPDATE User SET nom=?, prenom=?, mail=?, numero_telephone=?, motdepasse=?, description=?, education=?, github=?, experience=?   WHERE id=?";
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setInt(10, p.getId());
                pst.setString(1, p.getNom());
                pst.setString(2, p.getPrenom());
                pst.setString(3, p.getMail());
                pst.setInt(4, p.getNumero_telephone());
                pst.setString(5, p.getMotdepasse());
                pst.setString(6, p.getDescription());
                pst.setString(7, ((Candidat) p).getEducation().name());
                pst.setString(8, ((Candidat) p).getGithub());
                pst.setString(9, ((Candidat) p).getExperience().name());
                List<Candidat> list = afficherCandidat();
                Boolean candidatexiste = false;
                for (Candidat u : list) {
                    if (u.getMail().equals(p.getMail()) || u.getNumero_telephone() == p.getNumero_telephone()) {
                        candidatexiste = true;
                        break;
                    }
                }
                if (candidatexiste) {
                    throw new MailException("candidat existe deja");
                }
                pst.executeUpdate();
                System.out.println("Candidat modifiée !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        } else if (p instanceof Entreprise) {
            try {
                String req = "UPDATE User SET nom=?, prenom=?, mail=?, numero_telephone=?, motdepasse=?, description=?, NomEntreprise=?, role=?, TailleEntreprise=?, SiteWeb=?, Linkedin=?, id_domaine=?   WHERE id=?";
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setInt(13, p.getId());
                pst.setString(1, p.getNom());
                pst.setString(2, p.getPrenom());
                pst.setString(3, p.getMail());
                pst.setInt(4, p.getNumero_telephone());
                pst.setString(5, p.getMotdepasse());
                pst.setString(6, p.getDescription());
                pst.setString(7, ((Entreprise) p).getNomEntreprise());
                pst.setString(8, p.getClass().toString());
                pst.setString(9, ((Entreprise) p).getTailleEntreprise().name());
                pst.setString(10, ((Entreprise) p).getSiteWeb());
                pst.setString(11, ((Entreprise) p).getLinkedin());
                pst.setInt(12, ((Entreprise) p).getId_domaine());

                pst.executeUpdate();
                System.out.println("Entreprise modifiée !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                String req = "UPDATE User SET nom=?, prenom=?, mail=?, numero_telephone=?, motdepasse=?, description=?,role=?   WHERE id=?";
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setInt(8, p.getId());
                pst.setString(1, p.getNom());
                pst.setString(2, p.getPrenom());
                pst.setString(3, p.getMail());
                pst.setInt(4, p.getNumero_telephone());
                pst.setString(5, p.getMotdepasse());
                pst.setString(6, p.getDescription());
                pst.setString(7, p.getClass().getSimpleName());
                pst.executeUpdate();
                System.out.println("Admin modifiée !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void supprimer(User p) {
        try {
            String req = "DELETE from User WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, p.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (p instanceof Candidat) {
            System.out.println("Candidat supprimée !");
        } else if (p instanceof Entreprise) {
            System.out.println("Entreprise supprimée !");
        } else {
            System.out.println("Admin supprimée !");
        }
    }

    public List<User> afficher() throws MailException {
        List<User> list = new ArrayList<>();

        String req = "SELECT * FROM user";

        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String mail = rs.getString("mail");
                int numeroTelephone = rs.getInt("numero_telephone");
                String motdepasse = rs.getString("motdepasse");
                String description = rs.getString("description");
                String NomEntreprise = rs.getString("NomEntreprise");
                String role = rs.getString("role");
                if (role.equals("Candidat")) {
                    Diplome education = Diplome.valueOf(rs.getString("education"));
                    String Github = rs.getString("Github");
                    Experience experience = Experience.valueOf(rs.getString("experience"));
                    User candidat = new Candidat(id, nom, prenom, mail, numeroTelephone, motdepasse, description, education, Github, experience);
                    list.add(candidat);
                } else if (role.equals("Entreprise")) {
                    Taille TailleEntreprise = Taille.valueOf(rs.getString("TailleEntreprise"));
                    String SiteWeb = rs.getString("SiteWeb");
                    String Linkedin = rs.getString("Linkedin");
                    int id_domaine = rs.getInt("id_domaine");
                    User entreprise = new Entreprise(id, nom, prenom, mail, numeroTelephone, motdepasse, description, NomEntreprise, TailleEntreprise, SiteWeb, Linkedin, id_domaine);
                    list.add(entreprise);
                } else {
                    User admin = new Administrateur(id, nom, prenom, mail, numeroTelephone, motdepasse, description);
                    list.add(admin);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;

    }

    public class Entreprisedomaine extends Entreprise {

        private String nom_domaine;

        public Entreprisedomaine(int id, String nom, String prenom, String mail, int numero_telephone, String motdepasse, String description, String NomEntreprise, Taille TailleEntreprise, String SiteWeb, String Linkedin, int id_domaine, String nom_domaine) throws MailException {
            super(id, nom, prenom, mail, numero_telephone, motdepasse, description, NomEntreprise, TailleEntreprise, SiteWeb, Linkedin, id_domaine);
            this.nom_domaine = nom_domaine;
        }

        public String getNom_domaine() {
            return nom_domaine;
        }

        public void setNom_domaine(String nom_domaine) {
            this.nom_domaine = nom_domaine;
        }

        @Override
        public String toString() {
            return "entreprisedomaine{" + super.toString() + "nom_domaine=" + nom_domaine + '}';
        }

    }

    public List<Entreprisedomaine> afficherentreprise() throws MailException {
        List<Entreprisedomaine> list = new ArrayList<>();
        String req = "select id, nom, prenom, mail, numero_telephone, motdepasse, description, NomEntreprise, role, TailleEntreprise, SiteWeb, Linkedin, e.id_domaine, d.nom_domaine from User e join Domaine d on e.id_domaine=d.id_domaine;";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String mail = rs.getString("mail");
                int numeroTelephone = rs.getInt("numero_telephone");
                String motdepasse = rs.getString("motdepasse");
                String description = rs.getString("description");
                Taille TailleEntreprise = Taille.valueOf(rs.getString("TailleEntreprise"));
                String NomEntreprise = rs.getString("NomEntreprise");
                String SiteWeb = rs.getString("SiteWeb");
                String Linkedin = rs.getString("Linkedin");
                int id_domaine = rs.getInt("id_domaine");
                String nom_domaine = rs.getString("nom_domaine");
                Entreprisedomaine ed = new Entreprisedomaine(id, nom, prenom, mail, numeroTelephone, motdepasse, description, NomEntreprise, TailleEntreprise, SiteWeb, Linkedin, id_domaine, nom_domaine);
                list.add(ed);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public List<String> afficherDomainebynom() {
        List<String> list = new ArrayList<>();

        String req = "SELECT nom_domaine from Domaine";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                String nom = rs.getString("nom_domaine");

                list.add(nom);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public List<Candidat> afficherCandidat() throws MailException {
        List<Candidat> list = new ArrayList<>();
        String req = "select * from User;";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String mail = rs.getString("mail");
                int numeroTelephone = rs.getInt("numero_telephone");
                String motdepasse = rs.getString("motdepasse");
                String description = rs.getString("description");
                String role = rs.getString("role");
                if (role.equals("Candidat")) {
                    Diplome education = Diplome.valueOf(rs.getString("education"));
                    String Github = rs.getString("Github");
                    Experience experience = Experience.valueOf(rs.getString("experience"));
                    Candidat candidat = new Candidat(id, nom, prenom, mail, numeroTelephone, motdepasse, description, education, Github, experience);
                    list.add(candidat);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public ObservableList<User> getalluser() throws MailException {
        ObservableList<User> list = FXCollections.observableArrayList();
        boolean entrepriseajoutee = false;
        String req = "SELECT * FROM user";

        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String mail = rs.getString("mail");
                Integer numeroTelephone = rs.getInt("numero_telephone");
                String motdepasse = rs.getString("motdepasse");
                String description = rs.getString("description");
                String NomEntreprise = rs.getString("NomEntreprise");
                String role = rs.getString("role");
                if (role.equals("Candidat")) {
                    Diplome education = Diplome.valueOf(rs.getString("education"));
                    String Github = rs.getString("Github");
                    Experience experience = Experience.valueOf(rs.getString("experience"));
                    User candidat = new Candidat(id, nom, prenom, mail, numeroTelephone, motdepasse, description, education, Github, experience);
                    list.add(candidat);
                } else if (role.equals("Entreprise")) {

                    if (!entrepriseajoutee) {
                        list.addAll(afficherentreprise());
                        entrepriseajoutee = true;
                    }
                } else {
                    User admin = new Administrateur(id, nom, prenom, mail, numeroTelephone, motdepasse, description);
                    list.add(admin);
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;

    }

    public ObservableList<Candidat> getallcandidat() throws MailException {
        List l = afficherCandidat();
        ObservableList<Candidat> list = FXCollections.observableArrayList();
        list.addAll(l);
        return list;

    }

    public List<Administrateur> afficherAdmin() throws MailException {
        List<Administrateur> list = new ArrayList<>();
        String req = "select * from User;";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String mail = rs.getString("mail");
                int numeroTelephone = rs.getInt("numero_telephone");
                String motdepasse = rs.getString("motdepasse");
                String description = rs.getString("description");
                String role = rs.getString("role");
                if (role.equals("Administrateur")) {
                    Administrateur admin = new Administrateur(id, nom, prenom, mail, numeroTelephone, motdepasse, description);

                    list.add(admin);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public ObservableList<Administrateur> getalladmint() throws MailException {
        List l = afficherAdmin();
        ObservableList<Administrateur> list = FXCollections.observableArrayList();
        list.addAll(l);
        return list;

    }

    public boolean login(String login, String password) {
        String req = "select * from user where (mail=? or numero_telephone=?) and motdepasse=?;";
        
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, login);
            pst.setString(2, login);
            pst.setString(3, String.valueOf(Candidat.Codepasse(password)));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean loginpasse(String login) {
        String req = "select * from user where (mail=? or numero_telephone=?);";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, login);
            pst.setString(2, login);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void modifiermotdepasse(String motdepasse, String login) {
        String req = "UPDATE User SET motdepasse=? where (mail=? or CAST(numero_telephone AS CHAR)=?);";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, String.valueOf(User.Codepasse(motdepasse)));
            pst.setString(2, login);
            pst.setString(3, login);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Candidat> searchcandidat(String login) {
        String req = "select * from user where nom LIKE CONCAT('%', ?, '%') OR prenom LIKE CONCAT('%', ?, '%');";
        List<Candidat> list = new ArrayList<>();
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, login);
            pst.setString(2, login);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String mail = rs.getString("mail");
                int numeroTelephone = rs.getInt("numero_telephone");
                String motdepasse = rs.getString("motdepasse");
                String description = rs.getString("description");
                String role = rs.getString("role");
                if (role.equals("Candidat")) {
                    Diplome education = Diplome.valueOf(rs.getString("education"));
                    String Github = rs.getString("Github");
                    Experience experience = Experience.valueOf(rs.getString("experience"));
                    Candidat candidat = new Candidat(id, nom, prenom, mail, numeroTelephone, motdepasse, description, education, Github, experience);
                    list.add(candidat);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return list;
    }

    public ObservableList<Candidat> getsearchcandidat(String candidat) throws MailException {
        List l = searchcandidat(candidat);
        ObservableList<Candidat> list = FXCollections.observableArrayList();
        list.addAll(l);
        return list;

    }

    public List<Entreprisedomaine> searchentreprise(String login) throws MailException {
        String req = "select id, nom, prenom, mail, numero_telephone, motdepasse, description, NomEntreprise, role, TailleEntreprise, SiteWeb, Linkedin, e.id_domaine, d.nom_domaine from User e join Domaine d on e.id_domaine=d.id_domaine where NomEntreprise LIKE CONCAT('%', ?, '%');";
        List<Entreprisedomaine> list = new ArrayList<>();
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, login);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String mail = rs.getString("mail");
                int numeroTelephone = rs.getInt("numero_telephone");
                String motdepasse = rs.getString("motdepasse");
                String description = rs.getString("description");
                Taille TailleEntreprise = Taille.valueOf(rs.getString("TailleEntreprise"));
                String NomEntreprise = rs.getString("NomEntreprise");
                String SiteWeb = rs.getString("SiteWeb");
                String Linkedin = rs.getString("Linkedin");
                int id_domaine = rs.getInt("id_domaine");
                String nom_domaine = rs.getString("nom_domaine");
                Entreprisedomaine ed = new Entreprisedomaine(id, nom, prenom, mail, numeroTelephone, motdepasse, description, NomEntreprise, TailleEntreprise, SiteWeb, Linkedin, id_domaine, nom_domaine);
                list.add(ed);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public ObservableList<Entreprisedomaine> getsearchentreprise(String entreprise) throws MailException {
        List l = searchentreprise(entreprise);
        ObservableList<Entreprisedomaine> list = FXCollections.observableArrayList();
        list.addAll(l);
        return list;

    }

    public String idutilisateur(String login) {
        String role = "not found";
        String req = "select role from user where (mail=? or numero_telephone=?);";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, login);
            pst.setString(2, login);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                 role = rs.getString("role");
                
        }} catch (Exception e) {
            System.out.println(e.getMessage());
            return role;
        }
        return role;
        
    }
    
    public User getUserByID(int id) throws MailException {
        User u = null;
        String req = "SELECT * FROM user Where id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                u=new User(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("mail"), res.getInt("numero_telephone"),res.getString("motdepasse") , res.getString("description"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }
    
    public int getIdBymail(String mail) throws MailException {
        int id = 0 ;
        String req = "SELECT id FROM user Where mail = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, mail);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
               id= res.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
}
