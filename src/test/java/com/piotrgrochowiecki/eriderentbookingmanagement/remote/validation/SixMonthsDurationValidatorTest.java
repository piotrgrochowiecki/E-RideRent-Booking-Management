package com.piotrgrochowiecki.eriderentbookingmanagement.remote.validation;

import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SixMonthsDurationValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Given bookingRequestDto with duration longer than 6 months, " +
                 "when validation occurs" +
                 "then it should add violation to the set of violations")
    void shouldAddOneViolationToTheSet() {
        //given
        LocalDate startDate = LocalDate.of(2041, 10, 6);
        LocalDate endDate = LocalDate.of(2042, 8, 9);

        BookingRequestDto bookingRequestDto = BookingRequestDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .carUuid("carUuid")
                .userUuid("userUuid")
                .build();

        //when
        Set<ConstraintViolation<BookingRequestDto>> violations = validator.validate(bookingRequestDto);

        //then
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Given bookingRequestDto with duration shorter than 6 months, " +
                 "when validation occurs" +
                 "then it should not add any violation to the set of violations")
    void shouldNotAddAnyViolationToTheSet() {
        //given
        LocalDate startDate = LocalDate.of(2041, 10, 6);
        LocalDate endDate = LocalDate.of(2041, 11, 9);

        BookingRequestDto bookingRequestDto = BookingRequestDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .carUuid("carUuid")
                .userUuid("userUuid")
                .build();

        //when
        Set<ConstraintViolation<BookingRequestDto>> violations = validator.validate(bookingRequestDto);

        //then
        assertEquals(0, violations.size());
    }

}