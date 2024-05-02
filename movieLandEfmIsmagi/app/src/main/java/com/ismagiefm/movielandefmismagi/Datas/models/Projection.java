package com.ismagiefm.movielandefmismagi.Datas.models;

public class Projection {
    private int id;
    private String dateProjection;
    private double prix;
    private Film film;
    private Seance seance; // Assuming you have a Seance class
    private String langue;

    private Ticket ticket;

    public Projection(int id, String dateProjection, double prix, Film film, Seance seance, String langue) {
        this.id = id;
        this.dateProjection = dateProjection;
        this.prix = prix;
        this.film = film;
        this.seance = seance;
        this.langue = langue;
        this.ticket = null;
    }

    public int getId() {
        return id;
    }

    public String getDateProjection() {
        return dateProjection;
    }

    public double getPrix() {
        return prix;
    }

    public Film getFilm() {
        return film;
    }

    public Seance getSeance() {
        return seance;
    }



    public String getLangue() {
        return langue;
    }

    @Override
    public String toString() {
        return "Projection{" +
                "id=" + id +
                ", dateProjection='" + dateProjection + '\'' +
                ", prix=" + prix +
                ", film=" + film +
                ", seance=" + seance +
                ", langue='" + langue + '\'' +
                '}';
    }

    public Ticket getTicket() {
        return ticket;
    }
}