package org.ipt.smartqueue.data.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ipt.smartqueue.data.annotation.Rating;

public class RatingValidator implements ConstraintValidator<Rating, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value >= 1 && value <= 5;
    }
}
