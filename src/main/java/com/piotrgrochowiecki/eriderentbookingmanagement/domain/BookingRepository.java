package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookingRepository {

    Booking save(Booking booking);

    Optional<Booking> findById(Long id);

    Page<Booking> findAll(Pageable paging);

    List<Booking> findAllInMonthsOfStartAndEndDateAndInBetween(Set<Integer> yearsCoveringNewBookingDate,
                                                               List<Integer> monthsCoveringNewBookingDate,
                                                               int startYear,
                                                               int endYear,
                                                               int startMonth,
                                                               int endMonth);

}
