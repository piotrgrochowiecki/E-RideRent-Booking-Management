package com.piotrgrochowiecki.eriderentbookingmanagement.remote.validation;

import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDateConstraint, BookingRequestDto> {

    @Override
    public boolean isValid(BookingRequestDto bookingRequestDto, ConstraintValidatorContext context) {
        return bookingRequestDto.endDate().isAfter(bookingRequestDto.startDate());
    }

    @Override
    public void initialize(EndDateAfterStartDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
