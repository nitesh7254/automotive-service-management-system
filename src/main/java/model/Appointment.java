package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import validation.ValidAppointmentDate;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Vehicle number is required")
    @Pattern(
        regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{1,3}[0-9]{1,4}$",
        message = "Invalid vehicle registration number format"
    )
    private String vehicleNumber;

    @NotBlank(message = "Appointment date is required")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}$",
        message = "Appointment date must be in YYYY-MM-DD format"
    )
    @ValidAppointmentDate(
        message = "Appointment date must be a valid calendar date"
    )
    private String appointmentDate;

    @NotBlank(message = "Status is required")
    @Pattern(
        regexp = "BOOKED|CANCELLED",
        message = "Status must be BOOKED or CANCELLED"
    )
    private String status;

    public Appointment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}