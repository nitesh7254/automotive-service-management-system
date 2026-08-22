package ai;

import org.springframework.stereotype.Service;

@Service
public class ServiceTypeExtractor {

    public String extractServiceType(String message) {

        if (message == null || message.trim().isEmpty()) {
            return "GENERAL_SERVICE";
        }

        String text = message.toLowerCase().trim();

        if (text.contains("oil change")
                || text.contains("engine oil")
                || text.contains("oil service")) {

            return "OIL_CHANGE";
        }

        if (text.contains("brake")
                || text.contains("brake service")
                || text.contains("brakes")) {

            return "BRAKE_SERVICE";
        }

        if (text.contains("battery")
                || text.contains("battery problem")
                || text.contains("battery replacement")) {

            return "BATTERY_SERVICE";
        }

        if (text.contains("tyre")
                || text.contains("tire")
                || text.contains("wheel alignment")
                || text.contains("puncture")) {

            return "TYRE_SERVICE";
        }

        if (text.contains("engine")
                || text.contains("engine service")) {

            return "ENGINE_SERVICE";
        }

        if (text.contains("ac")
                || text.contains("air conditioning")
                || text.contains("air conditioner")) {

            return "AC_SERVICE";
        }

        if (text.contains("general service")
                || text.contains("maintenance")
                || text.contains("servicing")) {

            return "GENERAL_SERVICE";
        }

        return "GENERAL_SERVICE";
    }
}