package com.ismagiefm.movielandefmismagi.Datas.models;

import java.util.Date;

public class Reservation {
    private int id;
    private String nomClient;
    private String dateReservation;
    private int nombreTickets;

    private String numeroTelephone;

    private Ticket ticket;

    private Projection projection;
    private User user;
    private Long userId;




    public Reservation() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNomClient() {
        return nomClient;
    }


    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }


    public String getDateReservation() {
        return dateReservation;
    }


    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }


    public int getNombreTickets() {
        return nombreTickets;
    }


    public void setNombreTickets(int nombreTickets) {
        this.nombreTickets = nombreTickets;
    }


    public String getNumeroTelephone() {
        return numeroTelephone;
    }


    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }


    public Ticket getTicket() {
        return ticket;
    }


    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


    public Projection getProjection() {
        return projection;
    }


    public void setProjection(Projection projection) {
        this.projection = projection;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
