package ai;

import org.springframework.stereotype.Service;

@Service
public class IntentService {

    public String detectIntent(String message) {

        if (message == null || message.trim().isEmpty()) {

            return "UNKNOWN";
        }

        String text = message.toLowerCase().trim();

        // 1. Greeting
        if (text.contains("hello")
                || text.contains("hi")
                || text.contains("hey")
                || text.contains("good morning")
                || text.contains("good evening")) {

            return "GREETING";
        }

        // 2. Vehicle issue
        if (text.contains("not starting")
                || text.contains("breakdown")
                || text.contains("engine problem")
                || text.contains("engine issue")
                || text.contains("car problem")
                || text.contains("car issue")) {

            return "VEHICLE_ISSUE";
        }

        // 3. Appointment rescheduling
        if ((text.contains("reschedule")
                || text.contains("rescheduling")
                || text.contains("change my appointment")
                || text.contains("change appointment"))
                && (text.contains("appointment")
                || text.contains("booking")
                || text.contains("book"))) {

            return "APPOINTMENT_RESCHEDULE";
        }

        // 4. Appointment cancellation
        if ((text.contains("cancel")
                || text.contains("cancellation"))
                && (text.contains("appointment")
                || text.contains("booking")
                || text.contains("book"))) {

            return "APPOINTMENT_CANCEL";
        }

        // 5. Appointment status
        if ((text.contains("appointment")
                || text.contains("booking"))
                && (text.contains("status")
                || text.contains("state"))) {

            return "APPOINTMENT_STATUS";
        }

        // 6. Service rescheduling
        // IMPORTANT:
        // This must come before the general appointment condition.
        if ((text.contains("reschedule")
                || text.contains("rescheduling")
                || text.contains("change my service")
                || text.contains("change service"))
                && (text.contains("service")
                || text.contains("servicing")
                || text.contains("maintenance"))) {

            return "SERVICE_RESCHEDULE";
        }

        // 7. Appointment booking
        // Do not allow "reschedule" to match "schedule".
        if (text.contains("appointment")
                || text.contains("book")
                || (text.contains("schedule")
                && !text.contains("reschedule")
                && !text.contains("rescheduling"))) {

            return "APPOINTMENT";
        }

        // 8. Price inquiry
        if (text.contains("price")
                || text.contains("cost")
                || text.contains("how much")
                || text.contains("pricing")) {

            return "PRICE_INQUIRY";
        }

        // 9. Vehicle status
        if (text.contains("vehicle status")
                || text.contains("car status")
                || text.contains("where is my car")
                || text.contains("status of my car")) {

            return "VEHICLE_STATUS";
        }

        // 10. Service cancellation
        if ((text.contains("cancel")
                || text.contains("cancellation"))
                && (text.contains("service")
                || text.contains("servicing")
                || text.contains("maintenance"))) {

            return "SERVICE_CANCEL";
        }

        // 11. Service status
        if ((text.contains("service")
                || text.contains("servicing"))
                && (text.contains("status")
                || text.contains("state"))) {

            return "SERVICE_STATUS";
        }

        // 12. Service request
        if (text.contains("service")
                || text.contains("servicing")
                || text.contains("maintenance")
                || text.contains("oil change")) {

            return "SERVICE_REQUEST";
        }

        return "UNKNOWN";
    }
}