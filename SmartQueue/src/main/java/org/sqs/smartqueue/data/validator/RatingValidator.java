package org.sqs.smartqueue.data.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sqs.smartqueue.data.annotation.Rating;

public class RatingValidator implements ConstraintValidator<Rating, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value >= 1 && value <= 5;
    }
}
