package com.piotrgrochowiecki.eriderentbookingmanagement.remote.validation;

import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.temporal.ChronoUnit;

public class SixMonthsDurationValidator implements ConstraintValidator<SixMonthsDurationConstraint, BookingRequestDto> {

    @Override
    public void initialize(SixMonthsDurationConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BookingRequestDto bookingRequestDto, ConstraintValidatorContext context) {
        long durationInMonths = ChronoUnit.MONTHS.between(bookingRequestDto.startDate(), bookingRequestDto.endDate());
        return durationInMonths <= 6;
    }

}
