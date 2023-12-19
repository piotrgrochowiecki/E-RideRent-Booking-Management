package com.piotrgrochowiecki.eriderentbookingmanagement.remote.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SixMonthsDurationValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SixMonthsDurationConstraint {
    String message() default "Maximal booking duration is 6 months";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
