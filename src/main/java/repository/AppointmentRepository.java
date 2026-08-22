package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Appointment;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCustomerId(Long customerId);

    List<Appointment> findByVehicleNumber(String vehicleNumber);

    Optional<Appointment> findTopByVehicleNumberOrderByIdDesc(
            String vehicleNumber);

    List<Appointment> findByVehicleNumberAndAppointmentDate(
            String vehicleNumber,
            String appointmentDate);
}