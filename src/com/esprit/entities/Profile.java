package com.esprit.entities;

public class Profile {
    private int id_profile;
    private int id_user;
    private int id_competence;
    private Niveau niveau;

    
    
     public Profile() {
       
    }

    public Profile(int id_profile, int id_user, int id_competence, Niveau niveau) {
        this.id_profile = id_profile;
        this.id_user = id_user;
        this.id_competence = id_competence;
        this.niveau = niveau;
    }

    public int getId_profile() {
        return id_profile;
    }

    public void setId_profile(int id_profile) {
        this.id_profile = id_profile;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_competence() {
        return id_competence;
    }

    public void setId_competence(int id_competence) {
        this.id_competence = id_competence;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id_profile=" + id_profile +
                ", id_user=" + id_user +
                ", id_competence=" + id_competence +
                ", niveau=" + niveau +
                '}';
    }

    public enum Niveau {
        BESOIN_DE_TRAVAILLER_PLUS,
        CEST_BIEN_TU_PEUX_FAIRE_PLUS,
        PARFAIT
    }
}
