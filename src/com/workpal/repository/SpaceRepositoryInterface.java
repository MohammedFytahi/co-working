package com.workpal.repository;

import com.workpal.model.Space;

import java.sql.SQLException;
import java.util.List;

public interface SpaceRepositoryInterface {

    // Method to add a new space
    void createSpace(Space space) throws SQLException;

    // Method to retrieve a space by its ID
    Space getSpaceById(int idEspace) throws SQLException;

    // Method to retrieve all spaces
    List<Space> getAllSpaces() throws SQLException;

    // Method to update an existing space
    void updateSpace(Space space) throws SQLException;

    // Method to delete a space by its ID
    void deleteSpace(int idEspace) throws SQLException;
}
