package ai;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class VehicleNumberExtractor {

    private static final Pattern VEHICLE_NUMBER_PATTERN =
            Pattern.compile(
                    "\\b[A-Z]{2}[0-9]{1,2}[A-Z]{1,4}[0-9]{3,4}\\b",
                    Pattern.CASE_INSENSITIVE
            );

    public String extractVehicleNumber(String message) {

        if (message == null || message.trim().isEmpty()) {
            return null;
        }

        Matcher matcher =
                VEHICLE_NUMBER_PATTERN.matcher(
                        message.toUpperCase()
                );

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }
}	