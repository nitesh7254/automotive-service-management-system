package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
}