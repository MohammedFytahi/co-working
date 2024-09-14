package com.workpal.repository;

import com.workpal.model.Reservation;
import com.workpal.model.Space;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


public interface ReservationRepositoryInterface {
    void createReservation(Reservation reservation) throws SQLException;
    Reservation getReservationById(int idReservation) throws SQLException;
    List<Reservation> getReservationsByMembreId(int idMembre) throws SQLException;
    void deleteReservation(int idReservation) throws SQLException;
    List<Space> getReservedSpacesByMembreId(int idMembre) throws SQLException;
    List<Reservation> getReservationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException;
    void updateReservation(Reservation reservation) throws SQLException;
}
