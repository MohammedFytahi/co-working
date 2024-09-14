package com.workpal.service;

import com.workpal.model.Service;
import com.workpal.repository.ServiceRepositoryInterface;

import java.sql.SQLException;
import java.util.List;

public class ServiceService {
    private ServiceRepositoryInterface serviceRepository;

    public ServiceService(ServiceRepositoryInterface serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public void addService(Service service) throws SQLException {
        serviceRepository.addService(service);
    }

    public void updateService(Service service) throws SQLException {
        serviceRepository.updateService(service);
    }

    public void deleteService(int serviceId) throws SQLException {
        serviceRepository.deleteService(serviceId);
    }

    public List<Service> getAllServices() throws SQLException {
        return serviceRepository.getAllServices();
    }

    public Service getServiceById(int serviceId) throws SQLException {
        return serviceRepository.getServiceById(serviceId);
    }
}
