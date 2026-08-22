package service;

import java.util.List;

import org.springframework.stereotype.Service;

import exception.ResourceNotFoundException;
import model.Vehicle;
import repository.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    // =====================================================
    // CREATE
    // =====================================================

    public Vehicle createVehicle(Vehicle vehicle) {

        return repository.save(vehicle);
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<Vehicle> getAllVehicles() {

        return repository.findAll();
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public Vehicle getVehicleById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle with ID "
                                        + id
                                        + " not found"
                        )
                );
    }

    // =====================================================
    // GET BY REGISTRATION NUMBER
    // =====================================================

    public Vehicle getVehicleByRegistrationNumber(
            String registrationNumber) {

        return repository
                .findByRegistrationNumber(registrationNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle with registration number "
                                        + registrationNumber
                                        + " not found"
                        )
                );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public Vehicle updateVehicle(
            Long id,
            Vehicle vehicle) {

        Vehicle existingVehicle =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Vehicle with ID "
                                                + id
                                                + " not found"
                                )
                        );

        existingVehicle.setBrand(
                vehicle.getBrand()
        );

        existingVehicle.setModel(
                vehicle.getModel()
        );

        existingVehicle.setManufacturingYear(
                vehicle.getManufacturingYear()
        );

        existingVehicle.setRegistrationNumber(
                vehicle.getRegistrationNumber()
        );

        return repository.save(existingVehicle);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void deleteVehicle(Long id) {

        if (!repository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Vehicle with ID "
                            + id
                            + " not found"
            );
        }

        repository.deleteById(id);
    }
}