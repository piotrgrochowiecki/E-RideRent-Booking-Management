package com.piotrgrochowiecki.eriderentbookingmanagement.api.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookingResponseDto(Long id,
                                 LocalDate startDate,
                                 LocalDate endDate,
                                 String userUuid,
                                 String carUuid) {

}
