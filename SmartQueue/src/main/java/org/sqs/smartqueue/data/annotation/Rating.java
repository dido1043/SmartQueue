package org.sqs.smartqueue.data.annotation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.sqs.smartqueue.data.validator.RatingValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RatingValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Rating {
    String message() default "Rating must be between 1 and 5";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
