package com.piotrgrochowiecki.eriderentbookingmanagement.api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record BookingRequestDto(@NotNull @Future @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @NotNull @Future @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                @NotNull String userUuid,
                                @NotNull String carUuid) {

}