/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.esprit.entities.Commentaire;
import com.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author Acer
 */
public class ServiceCommentaire implements IServices<Commentaire> {

    private Connection cnx = DataSource.GetInstance().getCnx();
    private ServiceForum serviceForum;
    String[] wordsToReplace = {"fail", "kill", "bad"};

    public void ajouter(Commentaire C) {
        try {
            String req = "INSERT INTO commentaire(contenu, id_forum, id_user) VALUES (?,?,?);";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, replaceBadWords(C.getContenu()));
            pst.setInt(2, C.getId_forum());
            pst.setInt(3, C.getId_user());
            pst.executeUpdate();
            System.out.println("Commentaire ajoutée !");

            String senderEmail = "rafikpidev@gmail.com";
            String senderPassword = "jcugmepbjploduae";

            serviceForum = new ServiceForum();
            String recipientEmail = serviceForum.getUserEmailByForumId(C.getId_forum());

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            try {
                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

                message.setSubject(serviceForum.getUserNameByForumId(C.getId_user()) + " a Commenté sur votre forum");
                message.setText("Bonjour " + serviceForum.getUserNameByForumId(C.getId_forum()) + ",\n\nVous avez un nouveau commentaire sur votre forum par " + serviceForum.getUserNameByForumId(C.getId_user()) + ".\n"
                        + "Le contenu du commentaire est " + replaceBadWords(C.getContenu()) + "\n\nCordialement\nPI-Dev");
                Transport.send(message);

                System.out.println("Email sent successfully.");
            } catch (MessagingException e) {
                System.out.println("Failed to send email. Error: " + e.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Commentaire C) {
        try {
            String req = "UPDATE commentaire SET contenu=? WHERE id_commentaire=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, replaceBadWords(C.getContenu()));
            pst.setInt(2, C.getId_commentaire());
            pst.executeUpdate();
            System.out.println("Commentaire modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimer(Commentaire C) {
        try {
            String req = "DELETE from commentaire WHERE id_commentaire=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, C.getId_commentaire());
            pst.executeUpdate();
            System.out.println("Commentaire supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Commentaire> afficher() {
        List<Commentaire> list = new ArrayList<>();

        String req = "SELECT * FROM commentaire";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Commentaire(rs.getInt("id_commentaire"), rs.getString("contenu"), rs.getInt("id_forum"), rs.getInt("id_user")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public List<Commentaire> getCommentsForForum(int id_forum) {
        List<Commentaire> list = new ArrayList<>();

        String req = "SELECT * FROM commentaire where id_forum=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id_forum);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Commentaire(rs.getInt("id_commentaire"), rs.getString("contenu"), rs.getInt("id_forum"), rs.getInt("id_user")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public String replaceBadWords(String input) {
        for (String word : wordsToReplace) {
            String stars = "";
            for (int i = 0; i < word.length(); i++) {
                stars += "*";
            }
            input = input.replaceAll(word, stars);
        }

        return input;
    }

    public boolean userCanModifyOrDeleteCommentaire(Commentaire c, int id_user) {
        String query = "SELECT role,id FROM user WHERE id = ?;";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id_user);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                int iduser = rs.getInt("id");
                if ("Administrateur".equals(role) || iduser == c.getId_user()) {
                    System.out.println(role + "-" + iduser + "-" + c.getId_user());
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

}
