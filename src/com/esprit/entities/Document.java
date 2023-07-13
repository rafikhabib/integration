/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

/**
 *
 * author 2023
 */
public class Document {
    private int id_document;
    private String titre_document;
    private String description_document;
    private String type;//domaine
    private String lien;
    private int id_user;
  

    public Document() {
    }

    public Document(int id_document, String titre_document, String description_document, String type, String lien, int id_user) {
        this.id_document = id_document;
        this.titre_document = titre_document;
        this.description_document = description_document;
        this.type = type;
        this.lien = lien;
        this.id_user = id_user;
    }
    public Document(String titre, String type, String lien) {
        this.titre_document = titre;
        this.type = type;
        this.lien = lien;
    }
    
      public Document( String titre_document, String description_document, String type, String lien, int id_user) {
        
        this.titre_document = titre_document;
        this.description_document = description_document;
        this.type = type;
        this.lien = lien;
        this.id_user = id_user;
    }
      
      

    public Document(String title, String description, String type, String link, String userId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Document(String titre, String string, String string0, String type, String lien, int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getId_document() {
        return id_document;
    }

    public void setId_document(int id_document) {
        this.id_document = id_document;
    }

    public String getTitre_document() {
        return titre_document;
    }

    public void setTitre_document(String titre_document) {
        this.titre_document = titre_document;
    }

    public String getDescription_document() {
        return description_document;
    }

    public void setDescription_document(String description_document) {
        this.description_document = description_document;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id_document=" + id_document +
                ", titre_document='" + titre_document + '\'' +
                ", description_document='" + description_document + '\'' +
                ", type='" + type + '\'' +
                ", lien='" + lien + '\'' +
                ", id_user=" + id_user +
                '}';
    }

    
}
