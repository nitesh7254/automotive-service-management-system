package service;

import java.util.List;

import org.springframework.stereotype.Service;

import model.ServiceRecord;

import repository.ServiceRecordRepository;

@Service
public class ServiceRecordService {

    private final ServiceRecordRepository repository;

    public ServiceRecordService(
            ServiceRecordRepository repository) {

        this.repository = repository;
    }

    // CREATE
    public ServiceRecord createService(
            ServiceRecord serviceRecord) {

        if (serviceRecord.getStatus() == null
                || serviceRecord.getStatus().isBlank()) {

            serviceRecord.setStatus("PENDING");
        }

        return repository.save(serviceRecord);
    }

    // GET ALL
    public List<ServiceRecord> getAllServices() {

        return repository.findAll();
    }

    // GET BY ID
    public ServiceRecord getServiceById(Long id) {

        return repository
                .findById(id)
                .orElse(null);
    }

    // GET BY CUSTOMER
    public List<ServiceRecord> getServicesByCustomer(
            Long customerId) {

        return repository
                .findByCustomerId(customerId);
    }

    // GET BY VEHICLE
    public List<ServiceRecord> getServicesByVehicle(
            String vehicleNumber) {

        return repository
                .findByVehicleNumber(vehicleNumber);
    }

    // GET BY STATUS
    public List<ServiceRecord> getServicesByStatus(
            String status) {

        return repository
                .findByStatus(status);
    }

    // GET LATEST SERVICE BY VEHICLE
    public ServiceRecord getLatestServiceByVehicle(
            String vehicleNumber) {

        return repository
                .findTopByVehicleNumberOrderByIdDesc(
                        vehicleNumber)
                .orElse(null);
    }

    // CANCEL LATEST SERVICE BY VEHICLE
    public ServiceRecord cancelLatestServiceByVehicle(
            String vehicleNumber) {

        ServiceRecord serviceRecord =
                repository
                        .findTopByVehicleNumberOrderByIdDesc(
                                vehicleNumber)
                        .orElse(null);

        if (serviceRecord == null) {

            return null;
        }

        serviceRecord.setStatus("CANCELLED");

        return repository.save(serviceRecord);
    }

    // RESCHEDULE LATEST SERVICE BY VEHICLE
    public ServiceRecord rescheduleLatestServiceByVehicle(
            String vehicleNumber,
            String newServiceDate) {

        ServiceRecord serviceRecord =
                repository
                        .findTopByVehicleNumberOrderByIdDesc(
                                vehicleNumber)
                        .orElse(null);

        if (serviceRecord == null) {

            return null;
        }

        // Update service date
        serviceRecord.setServiceDate(
                newServiceDate);

        // Set service back to pending
        serviceRecord.setStatus(
                "PENDING");

        return repository.save(serviceRecord);
    }

    // UPDATE SERVICE
    public ServiceRecord updateService(
            Long id,
            ServiceRecord serviceRecord) {

        ServiceRecord existing =
                repository
                        .findById(id)
                        .orElse(null);

        if (existing == null) {

            return null;
        }

        existing.setVehicleNumber(
                serviceRecord.getVehicleNumber());

        existing.setServiceType(
                serviceRecord.getServiceType());

        existing.setDescription(
                serviceRecord.getDescription());

        existing.setServiceDate(
                serviceRecord.getServiceDate());

        existing.setStatus(
                serviceRecord.getStatus());

        return repository.save(existing);
    }

    // UPDATE STATUS
    public ServiceRecord updateServiceStatus(
            Long id,
            String status) {

        ServiceRecord existing =
                repository
                        .findById(id)
                        .orElse(null);

        if (existing == null) {

            return null;
        }

        existing.setStatus(status);

        return repository.save(existing);
    }

    // CANCEL SERVICE BY ID
    public ServiceRecord cancelService(Long id) {

        ServiceRecord existing =
                repository
                        .findById(id)
                        .orElse(null);

        if (existing == null) {

            return null;
        }

        existing.setStatus("CANCELLED");

        return repository.save(existing);
    }

    // DELETE
    public void deleteService(Long id) {

        repository.deleteById(id);
    }
}