package com.workpal.repository;

import com.workpal.model.Reservation;
import com.workpal.model.Space;

import java.sql.SQLException;
import java.util.List;


public interface ReservationRepositoryInterface {
    void createReservation(Reservation reservation) throws SQLException;
    Reservation getReservationById(int idReservation) throws SQLException;
    List<Reservation> getReservationsByMembreId(int idMembre) throws SQLException;
    void deleteReservation(int idReservation) throws SQLException;
    List<Space> getReservedSpacesByMembreId(int idMembre) throws SQLException;
}
