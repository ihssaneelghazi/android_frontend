package com.ismagiefm.movielandefmismagi.Datas.models;

import java.util.List;

public class Ticket {

    private int id;
    private List<Reservation> reservations;
    private double prix;
    private String codePayment;




    public Ticket(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getCodePayement() {
        return codePayment;
    }

    public void setCodePayement(String codePayement) {
        this.codePayment = codePayement;
    }


}
