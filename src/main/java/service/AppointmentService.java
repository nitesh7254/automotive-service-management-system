package service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import exception.ResourceNotFoundException;
import model.Appointment;
import repository.AppointmentRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    // =========================================================
    // CREATE
    // =========================================================

    public Appointment createAppointment(Appointment appointment) {

        if (appointment.getAppointmentDate() == null
                || appointment.getAppointmentDate().isBlank()) {

            appointment.setAppointmentDate(
                    LocalDate.now().toString()
            );
        }

        if (appointment.getStatus() == null
                || appointment.getStatus().isBlank()) {

            appointment.setStatus("BOOKED");
        }

        return repository.save(appointment);
    }

    // =========================================================
    // CHECK BY VEHICLE AND DATE
    // =========================================================

    public Appointment getAppointmentByVehicleAndDate(
            String vehicleNumber,
            String appointmentDate) {

        List<Appointment> appointments =
                repository.findByVehicleNumberAndAppointmentDate(
                        vehicleNumber,
                        appointmentDate
                );

        if (appointments.isEmpty()) {
            return null;
        }

        return appointments.get(appointments.size() - 1);
    }

    // =========================================================
    // GET ALL
    // =========================================================

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    public Appointment getAppointmentById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment with ID "
                                        + id
                                        + " not found"
                        )
                );
    }

    // =========================================================
    // GET BY CUSTOMER
    // =========================================================

    public List<Appointment> getAppointmentsByCustomer(
            Long customerId) {

        return repository.findByCustomerId(customerId);
    }

    // =========================================================
    // GET BY VEHICLE
    // =========================================================

    public List<Appointment> getAppointmentsByVehicle(
            String vehicleNumber) {

        return repository.findByVehicleNumber(vehicleNumber);
    }

    // =========================================================
    // GET LATEST APPOINTMENT BY VEHICLE
    // =========================================================

    public Appointment getLatestAppointmentByVehicle(
            String vehicleNumber) {

        return repository
                .findTopByVehicleNumberOrderByIdDesc(
                        vehicleNumber
                )
                .orElse(null);
    }

    // =========================================================
    // CANCEL LATEST APPOINTMENT BY VEHICLE
    // =========================================================

    public Appointment cancelLatestAppointmentByVehicle(
            String vehicleNumber) {

        Appointment appointment =
                repository
                        .findTopByVehicleNumberOrderByIdDesc(
                                vehicleNumber
                        )
                        .orElse(null);

        if (appointment == null) {
            return null;
        }

        appointment.setStatus("CANCELLED");

        return repository.save(appointment);
    }

    // =========================================================
    // RESCHEDULE LATEST APPOINTMENT BY VEHICLE
    // =========================================================

    public Appointment rescheduleLatestAppointmentByVehicle(
            String vehicleNumber,
            String newDate) {

        Appointment appointment =
                repository
                        .findTopByVehicleNumberOrderByIdDesc(
                                vehicleNumber
                        )
                        .orElse(null);

        if (appointment == null) {
            return null;
        }

        appointment.setAppointmentDate(newDate);

        if ("CANCELLED".equalsIgnoreCase(
                appointment.getStatus())) {

            appointment.setStatus("BOOKED");
        }

        return repository.save(appointment);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    public Appointment updateAppointment(
            Long id,
            Appointment appointment) {

        Appointment existingAppointment =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment with ID "
                                                + id
                                                + " not found"
                                )
                        );

        // Update vehicle number
        if (appointment.getVehicleNumber() != null
                && !appointment.getVehicleNumber().isBlank()) {

            existingAppointment.setVehicleNumber(
                    appointment.getVehicleNumber()
            );
        }

        // Update appointment date
        if (appointment.getAppointmentDate() != null
                && !appointment.getAppointmentDate().isBlank()) {

            existingAppointment.setAppointmentDate(
                    appointment.getAppointmentDate()
            );
        }

        // Update status
        if (appointment.getStatus() != null
                && !appointment.getStatus().isBlank()) {

            existingAppointment.setStatus(
                    appointment.getStatus()
            );
        }

        return repository.save(existingAppointment);
    }

    // =========================================================
    // CANCEL BY ID
    // =========================================================

    public Appointment cancelAppointment(Long id) {

        Appointment appointment =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment with ID "
                                                + id
                                                + " not found"
                                )
                        );

        appointment.setStatus("CANCELLED");

        return repository.save(appointment);
    }

    // =========================================================
    // DELETE
    // =========================================================

    public void deleteAppointment(Long id) {

        if (!repository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Appointment with ID "
                            + id
                            + " not found"
            );
        }

        repository.deleteById(id);
    }
}