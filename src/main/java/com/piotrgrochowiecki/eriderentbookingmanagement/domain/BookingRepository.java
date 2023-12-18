package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookingRepository {

    Booking save(Booking booking);

    Optional<Booking> findById(Long id);

    List<Booking> findAll();

    List<Booking> findAllInMonthsOfStartAndEndDateAndInBetween(Set<Integer> yearsCoveringNewBookingDate,
                                                               List<Integer> monthsCoveringNewBookingDate,
                                                               int startYear,
                                                               int endYear,
                                                               int startMonth,
                                                               int endMonth);

}
