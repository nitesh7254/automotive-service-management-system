package ai;

import org.springframework.stereotype.Service;

import model.Appointment;
import model.ServiceRecord;
import model.Vehicle;

@Service
public class AiResponseService {

    public String generateResponse(
            String intent,
            String message,
            Vehicle vehicle,
            ServiceRecord latestService,
            Appointment latestAppointment) {

        String text = message == null
                ? ""
                : message.toLowerCase().trim();

        switch (intent) {

            // =====================================================
            // GREETING
            // =====================================================

            case "GREETING":

                return "Hello! Welcome to Automotive AI Support. "
                        + "How can I help you today?";


            // =====================================================
            // VEHICLE ISSUE
            // =====================================================

            case "VEHICLE_ISSUE":

                if (vehicle != null) {

                    return "I'm sorry to hear that your "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + " is having an issue. "
                            + "Please describe the problem in more detail "
                            + "so I can assist you.";
                }

                return "I'm sorry to hear that your vehicle is having "
                        + "an issue. Please provide a valid vehicle "
                        + "registration number.";


            // =====================================================
            // SERVICE REQUEST
            // =====================================================

            case "SERVICE_REQUEST":

                if (vehicle != null) {

                    return "Sure! I can help you with servicing your "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + ". Please provide your preferred service date.";
                }

                return "Sure! I can help you with vehicle servicing. "
                        + "Please provide your vehicle registration number "
                        + "and preferred service date.";


            // =====================================================
            // PRICE INQUIRY
            // =====================================================

            case "PRICE_INQUIRY":

                // Vehicle is available and user mentioned oil change
                if (vehicle != null && text.contains("oil change")) {

                    return "The price for an oil change for your "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + " depends on the service requirements. "
                            + "Please contact our service center for the "
                            + "exact price.";
                }

                // Vehicle is available but service is not clearly mentioned
                if (vehicle != null) {

                    return "I can help you with service pricing for your "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + ". Please tell me which service "
                            + "you are interested in.";
                }

                // No vehicle found but service was mentioned
                if (text.contains("oil change")) {

                    return "I can help you with the price of an oil change. "
                            + "Please provide your vehicle registration number "
                            + "so I can assist you with the applicable pricing.";
                }

                return "I can help you with service pricing. "
                        + "Please provide your vehicle registration number "
                        + "and tell me which service you are interested in.";


            // =====================================================
            // SERVICE STATUS
            // =====================================================

            case "SERVICE_STATUS":

                if (vehicle == null) {

                    return "I could not find the vehicle. "
                            + "Please provide a valid registration number.";
                }

                if (latestService != null) {

                    return "I found your "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + ". Your latest service is "
                            + latestService.getServiceType()
                            + " and the current status is "
                            + latestService.getStatus()
                            + ".";
                }

                return "I found your "
                        + vehicle.getBrand()
                        + " "
                        + vehicle.getModel()
                        + ", but I could not find any service "
                        + "records for this vehicle.";


            // =====================================================
            // SERVICE CANCEL
            // =====================================================

            case "SERVICE_CANCEL":

                if (vehicle == null) {

                    return "I could not find the vehicle. "
                            + "Please provide a valid vehicle "
                            + "registration number.";
                }

                if (latestService != null) {

                    return "Your latest service for "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + " has been cancelled successfully. "
                            + "The service status is "
                            + latestService.getStatus()
                            + ".";
                }

                return "I could not find any service "
                        + "record for your vehicle.";


            // =====================================================
            // SERVICE RESCHEDULE
            // =====================================================

            case "SERVICE_RESCHEDULE":

                if (vehicle == null) {

                    return "I could not find the vehicle. "
                            + "Please provide a valid vehicle "
                            + "registration number.";
                }

                if (latestService != null) {

                    String newDate =
                            formatDate(
                                    latestService.getServiceDate());

                    return "Your service for "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + " has been rescheduled successfully "
                            + "to "
                            + newDate
                            + ". Your service status is "
                            + latestService.getStatus()
                            + ".";
                }

                return "I could not find any service "
                        + "record for your vehicle.";


            // =====================================================
            // APPOINTMENT STATUS
            // =====================================================

            case "APPOINTMENT_STATUS":

                if (vehicle == null) {

                    return "I could not find the vehicle. "
                            + "Please provide a valid registration number.";
                }

                if (latestAppointment != null) {

                    String appointmentDate =
                            formatDate(
                                    latestAppointment
                                            .getAppointmentDate());

                    return "I found your "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + ". Your appointment status is "
                            + latestAppointment.getStatus()
                            + " and your appointment date is "
                            + appointmentDate
                            + ".";
                }

                return "I found your "
                        + vehicle.getBrand()
                        + " "
                        + vehicle.getModel()
                        + ", but I could not find any appointment "
                        + "for this vehicle.";


            // =====================================================
            // VEHICLE STATUS
            // =====================================================

            case "VEHICLE_STATUS":

                if (vehicle == null) {

                    return "I could not find the vehicle. "
                            + "Please provide a valid registration number.";
                }

                if (latestAppointment != null) {

                    String formattedDate =
                            formatDate(
                                    latestAppointment
                                            .getAppointmentDate());

                    return "I found your "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + ". Your latest appointment status is "
                            + latestAppointment.getStatus()
                            + " and the appointment date is "
                            + formattedDate
                            + ".";
                }

                if (latestService != null) {

                    return "I found your "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + ". Your latest service is "
                            + latestService.getServiceType()
                            + " and the current status is "
                            + latestService.getStatus()
                            + ".";
                }

                return "I found your "
                        + vehicle.getBrand()
                        + " "
                        + vehicle.getModel()
                        + ", but I could not find any service "
                        + "or appointment records for this vehicle.";


            // =====================================================
            // APPOINTMENT BOOKING
            // =====================================================

            case "APPOINTMENT":

                if (vehicle == null) {

                    return "I could not find the vehicle. "
                            + "Please provide a valid vehicle "
                            + "registration number.";
                }

                if (latestAppointment != null) {

                    String appointmentDate =
                            formatDate(
                                    latestAppointment
                                            .getAppointmentDate());

                    return "Your service appointment for "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + " has been booked successfully "
                            + "for "
                            + appointmentDate
                            + ". Your appointment status is "
                            + latestAppointment.getStatus()
                            + ".";
                }

                return "Sure! I can help you schedule a service "
                        + "appointment for your "
                        + vehicle.getBrand()
                        + " "
                        + vehicle.getModel()
                        + ".";


            // =====================================================
            // APPOINTMENT CANCEL
            // =====================================================

            case "APPOINTMENT_CANCEL":

                if (vehicle == null) {

                    return "I could not find the vehicle. "
                            + "Please provide a valid vehicle "
                            + "registration number.";
                }

                if (latestAppointment != null) {

                    return "Your appointment for "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + " has been cancelled successfully. "
                            + "The appointment status is "
                            + latestAppointment.getStatus()
                            + ".";
                }

                return "I could not find any appointment "
                        + "for your vehicle.";


            // =====================================================
            // APPOINTMENT RESCHEDULE
            // =====================================================

            case "APPOINTMENT_RESCHEDULE":

                if (vehicle == null) {

                    return "I could not find the vehicle. "
                            + "Please provide a valid vehicle "
                            + "registration number.";
                }

                if (latestAppointment != null) {

                    String newDate =
                            formatDate(
                                    latestAppointment
                                            .getAppointmentDate());

                    return "Your appointment for "
                            + vehicle.getBrand()
                            + " "
                            + vehicle.getModel()
                            + " has been rescheduled successfully "
                            + "to "
                            + newDate
                            + ". Your appointment status is "
                            + latestAppointment.getStatus()
                            + ".";
                }

                return "I could not find any appointment "
                        + "for your vehicle.";


            // =====================================================
            // DEFAULT
            // =====================================================

            default:

                return "I'm sorry, I didn't fully understand your request. "
                        + "Could you please provide more details about "
                        + "your vehicle or service requirement?";
        }
    }


    // =========================================================
    // DATE FORMATTER
    // yyyy-MM-dd → dd MMMM yyyy
    // =========================================================

    private String formatDate(String date) {

        if (date == null || date.isBlank()) {

            return "not scheduled";
        }

        try {

            String[] parts = date.split("-");

            if (parts.length == 3) {

                String year = parts[0];
                String month = parts[1];
                String day = parts[2];

                String[] months = {

                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December"
                };

                int monthNumber =
                        Integer.parseInt(month);

                if (monthNumber >= 1
                        && monthNumber <= 12) {

                    return day
                            + " "
                            + months[monthNumber - 1]
                            + " "
                            + year;
                }
            }

        } catch (Exception e) {

            // Keep original date if formatting fails
        }

        return date;
    }
}		