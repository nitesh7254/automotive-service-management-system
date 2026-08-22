package controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import model.Appointment;
import service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public Appointment createAppointment(
            @Valid @RequestBody Appointment appointment) {

        return service.createAppointment(appointment);
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public List<Appointment> getAllAppointments() {

        return service.getAllAppointments();
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public Appointment getAppointmentById(
            @PathVariable Long id) {

        return service.getAppointmentById(id);
    }

    // =====================================================
    // GET BY CUSTOMER
    // =====================================================

    @GetMapping("/customer/{customerId}")
    public List<Appointment> getByCustomer(
            @PathVariable Long customerId) {

        return service.getAppointmentsByCustomer(customerId);
    }

    // =====================================================
    // GET BY VEHICLE
    // =====================================================

    @GetMapping("/vehicle/{vehicleNumber}")
    public List<Appointment> getByVehicle(
            @PathVariable String vehicleNumber) {

        return service.getAppointmentsByVehicle(vehicleNumber);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public Appointment updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody Appointment appointment) {

        return service.updateAppointment(id, appointment);
    }

    // =====================================================
    // CANCEL BY ID
    // =====================================================

    @PutMapping("/{id}/cancel")
    public Appointment cancelAppointment(
            @PathVariable Long id) {

        return service.cancelAppointment(id);
    }

    // =====================================================
    // RESCHEDULE LATEST APPOINTMENT BY VEHICLE
    // =====================================================

    @PutMapping("/vehicle/{vehicleNumber}/reschedule")
    public Appointment rescheduleAppointment(
            @PathVariable String vehicleNumber,
            @RequestParam String newDate) {

        return service.rescheduleLatestAppointmentByVehicle(
                vehicleNumber,
                newDate
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public String deleteAppointment(
            @PathVariable Long id) {

        service.deleteAppointment(id);

        return "Appointment deleted successfully";
    }
}