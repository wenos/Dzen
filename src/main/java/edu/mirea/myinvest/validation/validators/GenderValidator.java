package edu.mirea.myinvest.validation.validators;


import edu.mirea.myinvest.domain.model.UserGender;
import edu.mirea.myinvest.validation.constraints.ValidGender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(s)) {
            return true;
        }
        try {
            var gender = UserGender.valueOf(s);
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
