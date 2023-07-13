/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

/**
 *
 * @author Anis
 */
public class Administrateur extends User {

    public Administrateur(int id, String nom, String prenom, String mail, Integer numero_telephone, String motdepasse, String description) throws MailException {
        super(id, nom, prenom, mail, numero_telephone, motdepasse, description);
        if (!emailvalidator(mail)) {
            throw new MailException("Mail invalide");
        }

        if (numero_telephone.toString().length() != 8) {
            throw new MailException("Le numéro de téléphone doit contenir 8 chiffres");
        }
        if (motdepasse.length() < 8) {
            throw new MailException("Le mot de passe ne doit pas être inferieur à 8 caractéres");
        }
    }

    public Administrateur(String nom, String prenom, String mail, Integer numero_telephone, String motdepasse, String description) throws MailException {
        super(nom, prenom, mail, numero_telephone, motdepasse, description);
        if (!emailvalidator(mail)) {
            throw new MailException("Mail invalide");
        }

        if (numero_telephone.toString().length() != 8) {
            throw new MailException("Le numéro de téléphone doit contenir 8 chiffres");
        }
        if (motdepasse.length() < 8) {
            throw new MailException("Le mot de passe ne doit pas être inferieur à 8 caractéres");
        }
    }

    
    
    
    
}
