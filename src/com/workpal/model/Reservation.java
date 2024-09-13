package com.workpal.model;

import java.time.LocalDateTime;

public class Reservation {
    private int idReservation;
    private int idMembre;
    private int idEspace;
    private LocalDateTime dateReservation;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    public Reservation(int idReservation, int idMembre, int idEspace, LocalDateTime dateReservation, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.idReservation = idReservation;
        this.idMembre = idMembre;
        this.idEspace = idEspace;
        this.dateReservation = dateReservation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Reservation(int idMembre, int idEspace, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.idMembre = idMembre;
        this.idEspace = idEspace;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Reservation(int membreId, int espaceId, LocalDateTime dateTimeReservation) {
    }

    // Getters and setters

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public int getIdEspace() {
        return idEspace;
    }

    public void setIdEspace(int idEspace) {
        this.idEspace = idEspace;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", idMembre=" + idMembre +
                ", idEspace=" + idEspace +
                ", dateReservation=" + dateReservation +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}
