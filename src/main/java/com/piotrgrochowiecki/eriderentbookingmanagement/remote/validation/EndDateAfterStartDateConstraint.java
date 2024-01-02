package com.piotrgrochowiecki.eriderentbookingmanagement.remote.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EndDateAfterStartDateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDateAfterStartDateConstraint {
    String message() default "End date of booking must be after start date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
