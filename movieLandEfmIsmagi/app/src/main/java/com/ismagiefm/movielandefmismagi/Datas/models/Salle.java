package com.ismagiefm.movielandefmismagi.Datas.models;

public class Salle {
    private int id;
    private String name;
    private int nombrePlaces;

    public Salle(int id, String name, int nombrePlaces) {
        this.id = id;
        this.name = name;
        this.nombrePlaces = nombrePlaces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }



}
