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


    public void addSpace(Space space) throws SQLException {
        spaceRepository.createSpace(space);
    }


    public Space getSpaceById(int idEspace) throws SQLException {
        return spaceRepository.getSpaceById(idEspace);
    }


    public List<Space> getAllSpaces() throws SQLException {
        return spaceRepository.getAllSpaces();
    }


    public void updateSpace(Space space) throws SQLException {
        spaceRepository.updateSpace(space);
    }


    public void deleteSpace(int idEspace) throws SQLException {
        spaceRepository.deleteSpace(idEspace);
    }

    public List<Space> findSpacesByType(String typeEspace) {
        try {
            return spaceRepository.findSpacesByType(typeEspace);
        } catch (SQLException e) {
            System.err.println("Error finding spaces by type: " + e.getMessage());
            return null;
        }
    }

    public List<Space> getAvailableSpaces() {
        return spaceRepository.findAvailableSpaces();
    }

}
