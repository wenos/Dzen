package edu.mirea.vitality.blog.validation.validators;


import edu.mirea.vitality.blog.domain.model.Role;
import edu.mirea.vitality.blog.validation.constraints.ValidRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    private boolean nullable;

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(s)) {
            return nullable;
        }
        try {
            Role role = Role.valueOf(s);
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
