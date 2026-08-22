package ai;

import java.util.List;

import org.springframework.stereotype.Service;

import exception.ResourceNotFoundException;
import model.Appointment;
import model.Customer;
import model.Vehicle;
import service.AppointmentService;
import service.CustomerService;
import service.VehicleService;

@Service
public class AiSupportService {

    private final VehicleService vehicleService;
    private final AppointmentService appointmentService;
    private final CustomerService customerService;

    public AiSupportService(
            VehicleService vehicleService,
            AppointmentService appointmentService,
            CustomerService customerService) {

        this.vehicleService = vehicleService;
        this.appointmentService = appointmentService;
        this.customerService = customerService;
    }

    public String generateResponse(String message) {

        if (message == null || message.isBlank()) {
            return "Please enter your question.";
        }

        String input = message.toLowerCase().trim();

        // =====================================================
        // CANCEL APPOINTMENT
        // =====================================================

        if (input.contains("cancel")
                && input.contains("appointment")) {

            String registrationNumber =
                    extractRegistrationNumber(message);

            if (registrationNumber == null) {
                return "Please provide your vehicle registration number.";
            }

            Appointment appointment =
                    appointmentService
                            .cancelLatestAppointmentByVehicle(
                                    registrationNumber);

            if (appointment == null) {
                return "I could not find an appointment for vehicle "
                        + registrationNumber + ".";
            }

            return "Your latest appointment for vehicle "
                    + registrationNumber
                    + " has been cancelled successfully.";
        }

        // =====================================================
        // RESCHEDULE APPOINTMENT
        // =====================================================

        if (input.contains("reschedule")
                || input.contains("change appointment")) {

            String registrationNumber =
                    extractRegistrationNumber(message);

            if (registrationNumber == null) {
                return "Please provide your vehicle registration number.";
            }

            String newDate = extractDate(message);

            if (newDate == null) {
                return "Please provide the new appointment date "
                        + "in YYYY-MM-DD format.";
            }

            Appointment appointment =
                    appointmentService
                            .rescheduleLatestAppointmentByVehicle(
                                    registrationNumber,
                                    newDate);

            if (appointment == null) {
                return "I could not find an appointment for vehicle "
                        + registrationNumber + ".";
            }

            return "Your appointment has been rescheduled to "
                    + appointment.getAppointmentDate()
                    + ". Status: "
                    + appointment.getStatus() + ".";
        }

        // =====================================================
        // CUSTOMER INFORMATION
        // =====================================================

        if (input.contains("customer")
                || input.contains("customer details")
                || input.contains("my details")) {

            Long customerId = extractCustomerId(message);

            if (customerId == null) {
                return "Please provide your customer ID.";
            }

            Customer customer;

            try {

                customer =
                        customerService.getCustomerById(customerId);

            } catch (ResourceNotFoundException exception) {

                return "I could not find a customer with ID "
                        + customerId + ".";
            }

            return "Customer Details: "
                    + "ID: "
                    + customer.getId()
                    + ", Name: "
                    + customer.getName()
                    + ", Email: "
                    + customer.getEmail()
                    + ", Phone: "
                    + customer.getPhone();
        }

        // =====================================================
        // CHECK APPOINTMENTS
        // =====================================================

        if (input.contains("appointment")
                || input.contains("booking")
                || input.contains("service")) {

            String registrationNumber =
                    extractRegistrationNumber(message);

            if (registrationNumber == null) {
                return "Please provide your vehicle registration number.";
            }

            List<Appointment> appointments =
                    appointmentService
                            .getAppointmentsByVehicle(
                                    registrationNumber);

            if (appointments.isEmpty()) {
                return "I could not find any appointments for vehicle "
                        + registrationNumber + ".";
            }

            StringBuilder response =
                    new StringBuilder();

            response.append(
                    "Appointments for vehicle ")
                    .append(registrationNumber)
                    .append(":\n");

            for (Appointment appointment : appointments) {

                response.append("Appointment ID: ")
                        .append(appointment.getId())
                        .append(", Date: ")
                        .append(appointment.getAppointmentDate())
                        .append(", Status: ")
                        .append(appointment.getStatus())
                        .append("\n");
            }

            return response.toString();
        }

        // =====================================================
        // VEHICLE INFORMATION
        // =====================================================

        if (input.contains("vehicle")
                || input.contains("car")
                || input.contains("registration")) {

            String registrationNumber =
                    extractRegistrationNumber(message);

            if (registrationNumber == null) {
                return "Please provide your vehicle registration number.";
            }

            Vehicle vehicle;

            try {

                vehicle =
                        vehicleService
                                .getVehicleByRegistrationNumber(
                                        registrationNumber);

            } catch (ResourceNotFoundException exception) {

                return "I could not find a vehicle with registration number "
                        + registrationNumber + ".";
            }

            return "Vehicle Details: "
                    + "Registration Number: "
                    + vehicle.getRegistrationNumber()
                    + ", Brand: "
                    + vehicle.getBrand()
                    + ", Model: "
                    + vehicle.getModel()
                    + ", Manufacturing Year: "
                    + vehicle.getManufacturingYear();
        }

        // =====================================================
        // GREETING
        // =====================================================

        if (input.matches(
                "^(hello|hi|hey|good morning|good afternoon|good evening)[!. ]*$")) {

            return "Hello! Welcome to Automotive AI Customer Support. "
                    + "How can I help you today?";
        }

        // =====================================================
        // DEFAULT
        // =====================================================

        return "I'm here to help with customer information, "
                + "vehicle information, service appointments, "
                + "booking, cancellation, and rescheduling.";
    }

    // =========================================================
    // EXTRACT REGISTRATION NUMBER
    // =========================================================

    private String extractRegistrationNumber(String message) {

        String[] words = message.split("\\s+");

        for (String word : words) {

            String cleaned = word
                    .replaceAll("[^a-zA-Z0-9]", "")
                    .toUpperCase();

            if (cleaned.matches(
                    "[A-Z]{2}[0-9]{2}[A-Z]{1,3}[0-9]{1,4}")) {

                return cleaned;
            }
        }

        return null;
    }

    // =========================================================
    // EXTRACT CUSTOMER ID
    // =========================================================

    private Long extractCustomerId(String message) {

        String[] words = message.split("\\s+");

        for (int i = 0; i < words.length; i++) {

            String cleaned =
                    words[i].replaceAll("[^0-9]", "");

            if (!cleaned.isEmpty()) {

                if (i > 0
                        && (words[i - 1]
                                .equalsIgnoreCase("customer")
                        || words[i - 1]
                                .equalsIgnoreCase("id"))) {

                    try {

                        return Long.parseLong(cleaned);

                    } catch (NumberFormatException e) {

                        return null;
                    }
                }
            }
        }

        return null;
    }

    // =========================================================
    // EXTRACT DATE
    // =========================================================

    private String extractDate(String message) {

        String[] words = message.split("\\s+");

        for (String word : words) {

            String cleaned = word
                    .replaceAll("[^0-9-]", "");

            if (cleaned.matches(
                    "\\d{4}-\\d{2}-\\d{2}")) {

                return cleaned;
            }
        }

        return null;
    }
}