package com.ismagiefm.movielandefmismagi.Datas.models;

public class Seance {
private int id;
    private String heureDebut;
    private String heureFin;

    public Seance(int id, String heureDebut, String heureFin) {
        this.id = id;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public int getId() {
        return id;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }
}
