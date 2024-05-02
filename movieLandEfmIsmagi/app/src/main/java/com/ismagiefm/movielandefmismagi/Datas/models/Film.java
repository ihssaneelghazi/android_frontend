package com.ismagiefm.movielandefmismagi.Datas.models;

import android.widget.ImageView;

public class Film {
    private int id;
    private String titre;
    private double duree;
    private String realisateur;
    private String description;
    private String photo;
    private String dateSortie;
    private Category category; // Assuming you have a Category class




    public Film(int id, String titre, double duree, String realisateur, String description, String photo, String dateSortie, Category category) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.realisateur = realisateur;
        this.description = description;
        this.photo = photo;
        this.dateSortie = dateSortie;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public double getDuree() {
        return duree;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public Category getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", duree=" + duree +
                ", realisateur='" + realisateur + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", dateSortie='" + dateSortie + '\'' +
                ", category=" + category +
                '}';
    }


}
