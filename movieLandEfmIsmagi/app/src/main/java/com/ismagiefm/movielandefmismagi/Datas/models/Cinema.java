package com.ismagiefm.movielandefmismagi.Datas.models;

public class Cinema {
    private int id;
    private String name;

    // Constructeur
    public Cinema() {
    }

    // Getter et Setter pour l'ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et Setter pour le nom du cinéma
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
