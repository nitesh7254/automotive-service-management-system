package ai;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class DateExtractor {

    // ---------------------------------------------------------
    // Date patterns
    // ---------------------------------------------------------

    private static final Pattern NUMERIC_DATE =
            Pattern.compile(
                    "\\b(\\d{1,2})[-/](\\d{1,2})[-/](\\d{4})\\b");

    private static final Pattern ISO_DATE =
            Pattern.compile(
                    "\\b(\\d{4})-(\\d{1,2})-(\\d{1,2})\\b");

    private static final Pattern TEXT_DATE =
            Pattern.compile(
                    "\\b(\\d{1,2})\\s+"
                    + "(January|February|March|April|May|June|"
                    + "July|August|September|October|November|December)"
                    + "\\s+(\\d{4})\\b",
                    Pattern.CASE_INSENSITIVE);

    private static final Pattern SHORT_TEXT_DATE =
            Pattern.compile(
                    "\\b(\\d{1,2})\\s+"
                    + "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)"
                    + "\\s+(\\d{4})\\b",
                    Pattern.CASE_INSENSITIVE);


    // ---------------------------------------------------------
    // Extract date
    // ---------------------------------------------------------

    public String extractDate(String message) {

        if (message == null || message.trim().isEmpty()) {

            return null;
        }

        String text = message.trim();


        // -----------------------------------------------------
        // 1. yyyy-MM-dd
        // Example:
        // 2026-09-05
        // -----------------------------------------------------

        Matcher isoMatcher =
                ISO_DATE.matcher(text);

        if (isoMatcher.find()) {

            try {

                int year =
                        Integer.parseInt(
                                isoMatcher.group(1));

                int month =
                        Integer.parseInt(
                                isoMatcher.group(2));

                int day =
                        Integer.parseInt(
                                isoMatcher.group(3));

                LocalDate date =
                        LocalDate.of(
                                year,
                                month,
                                day);

                return date.toString();

            } catch (Exception e) {

                // Continue checking other formats
            }
        }


        // -----------------------------------------------------
        // 2. dd-MM-yyyy or dd/MM/yyyy
        // Example:
        // 05-09-2026
        // 5/9/2026
        // -----------------------------------------------------

        Matcher numericMatcher =
                NUMERIC_DATE.matcher(text);

        if (numericMatcher.find()) {

            try {

                int day =
                        Integer.parseInt(
                                numericMatcher.group(1));

                int month =
                        Integer.parseInt(
                                numericMatcher.group(2));

                int year =
                        Integer.parseInt(
                                numericMatcher.group(3));

                LocalDate date =
                        LocalDate.of(
                                year,
                                month,
                                day);

                return date.toString();

            } catch (Exception e) {

                // Continue checking text dates
            }
        }


        // -----------------------------------------------------
        // 3. dd MMMM yyyy
        // Example:
        // 5 September 2026
        // 05 September 2026
        // -----------------------------------------------------

        Matcher textMatcher =
                TEXT_DATE.matcher(text);

        if (textMatcher.find()) {

            try {

                int day =
                        Integer.parseInt(
                                textMatcher.group(1));

                String monthText =
                        textMatcher.group(2);

                int year =
                        Integer.parseInt(
                                textMatcher.group(3));

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(
                                "d MMMM uuuu",
                                java.util.Locale.ENGLISH);

                LocalDate date =
                        LocalDate.parse(
                                day
                                        + " "
                                        + monthText
                                        + " "
                                        + year,
                                formatter);

                return date.toString();

            } catch (DateTimeParseException e) {

                // Continue checking short month
            }
        }


        // -----------------------------------------------------
        // 4. dd MMM yyyy
        // Example:
        // 5 Sep 2026
        // 05 Sep 2026
        // -----------------------------------------------------

        Matcher shortTextMatcher =
                SHORT_TEXT_DATE.matcher(text);

        if (shortTextMatcher.find()) {

            try {

                int day =
                        Integer.parseInt(
                                shortTextMatcher.group(1));

                String monthText =
                        shortTextMatcher.group(2);

                int year =
                        Integer.parseInt(
                                shortTextMatcher.group(3));

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(
                                "d MMM uuuu",
                                java.util.Locale.ENGLISH);

                LocalDate date =
                        LocalDate.parse(
                                day
                                        + " "
                                        + monthText
                                        + " "
                                        + year,
                                formatter);

                return date.toString();

            } catch (DateTimeParseException e) {

                return null;
            }
        }

        return null;
    }
}