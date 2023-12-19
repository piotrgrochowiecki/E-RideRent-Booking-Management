package com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto;

import com.piotrgrochowiecki.eriderentbookingmanagement.remote.validation.SixMonthsDurationConstraint;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@SixMonthsDurationConstraint
public record BookingRequestDto(@NotNull @Future @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @NotNull @Future @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                @NotEmpty String userUuid,
                                @NotEmpty String carUuid) {

}
