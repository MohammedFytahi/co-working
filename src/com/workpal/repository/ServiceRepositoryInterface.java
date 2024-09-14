package com.workpal.repository;

import com.workpal.model.Service;

import java.sql.SQLException;
import java.util.List;

public interface ServiceRepositoryInterface {
    void addService(Service service) throws SQLException;
    void updateService(Service service) throws SQLException;
    void deleteService(int serviceId) throws SQLException;
    List<Service> getAllServices() throws SQLException;
    Service getServiceById(int serviceId) throws SQLException;
}
