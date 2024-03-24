package edu.mirea.vitality.blog.validation.validators;


import edu.mirea.vitality.blog.domain.dto.system.UpdateConfigUnitRequest;
import edu.mirea.vitality.blog.domain.model.system.SystemPropertyKey;
import edu.mirea.vitality.blog.domain.model.system.ConfigFieldType;
import edu.mirea.vitality.blog.validation.constraints.ValidSystemProperty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class SystemPropertyValidator implements ConstraintValidator<ValidSystemProperty, UpdateConfigUnitRequest> {

    @Override
    public boolean isValid(UpdateConfigUnitRequest r, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(r)) {
            return false;
        }
        SystemPropertyKey key = null;
        try {
            key = SystemPropertyKey.valueOf(r.key());
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }

        if (key.getCustomHandler()) {
            return false;
        }

        if (key.getType().equals(ConfigFieldType.NUMBER)) {
            try {
                var val = Long.parseLong(r.value());
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (key.getType().equals(ConfigFieldType.BOOLEAN) &&
                (!r.value().equals("true") && !r.value().equals("false"))) {
            return false;
        }
        return true;
    }
}
