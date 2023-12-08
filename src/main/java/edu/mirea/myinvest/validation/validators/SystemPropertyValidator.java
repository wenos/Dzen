package edu.mirea.myinvest.validation.validators;


import edu.mirea.myinvest.domain.dto.system.UpdateConfigUnitRequest;
import edu.mirea.myinvest.domain.model.system.SystemPropertyKey;
import edu.mirea.myinvest.domain.model.system.ConfigFieldType;
import edu.mirea.myinvest.validation.constraints.ValidSystemProperty;
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
