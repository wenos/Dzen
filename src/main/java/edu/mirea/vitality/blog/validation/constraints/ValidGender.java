package edu.mirea.vitality.blog.validation.constraints;

import edu.mirea.vitality.blog.validation.validators.GenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {GenderValidator.class})
public @interface ValidGender {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Позволяет указать, что поле может быть null
     */
    boolean nullable() default false;
}
