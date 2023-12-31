package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Booking(Long id,
                      LocalDate startDate,
                      LocalDate endDate,
                      String userUuid,
                      String carUuid) {

}
