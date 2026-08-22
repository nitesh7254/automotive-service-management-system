package validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = AppointmentDateValidator.class)
@Target({
        FIELD,
        ANNOTATION_TYPE
})
@Retention(RUNTIME)
public @interface ValidAppointmentDate {

    String message() default
            "Appointment date must be a valid date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}