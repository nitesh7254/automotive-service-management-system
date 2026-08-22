package controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import model.Vehicle;
import service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public Vehicle createVehicle(
            @Valid @RequestBody Vehicle vehicle) {

        return service.createVehicle(vehicle);
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public List<Vehicle> getAllVehicles() {

        return service.getAllVehicles();
    }

    // =====================================================
    // GET BY REGISTRATION NUMBER
    // =====================================================

    @GetMapping("/registration/{registrationNumber}")
    public Vehicle getVehicleByRegistrationNumber(
            @PathVariable String registrationNumber) {

        return service.getVehicleByRegistrationNumber(
                registrationNumber);
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public Vehicle getVehicleById(
            @PathVariable Long id) {

        return service.getVehicleById(id);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public Vehicle updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody Vehicle vehicle) {

        return service.updateVehicle(id, vehicle);
    }

    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public String deleteVehicle(
            @PathVariable Long id) {

        service.deleteVehicle(id);

        return "Vehicle deleted successfully";
    }
}