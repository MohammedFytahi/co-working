package com.workpal.service;

import com.workpal.model.Space;
import com.workpal.repository.SpaceRepository;

import java.sql.SQLException;
import java.util.List;

public class SpaceService {
    private final SpaceRepository spaceRepository;


    public SpaceService(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    // Create a new space
    public void addSpace(Space space) throws SQLException {
        spaceRepository.createSpace(space);
    }

    // Retrieve a space by ID
    public Space getSpaceById(int idEspace) throws SQLException {
        return spaceRepository.getSpaceById(idEspace);
    }

    // Retrieve all spaces
    public List<Space> getAllSpaces() throws SQLException {
        return spaceRepository.getAllSpaces();
    }

    // Update an existing space
    public void updateSpace(Space space) throws SQLException {
        spaceRepository.updateSpace(space);
    }

    // Delete a space by ID
    public void deleteSpace(int idEspace) throws SQLException {
        spaceRepository.deleteSpace(idEspace);
    }

    // Additional business logic methods can be added here
}
