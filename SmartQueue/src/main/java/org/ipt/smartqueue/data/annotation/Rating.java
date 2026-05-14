package org.ipt.smartqueue.data.annotation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ipt.smartqueue.data.validator.RatingValidator;

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
