package com.piotrgrochowiecki.eriderentbookingmanagement.data;

import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
@AllArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingJpaRepository bookingJpaRepository;
    private final BookingMapper bookingMapper;

    @Override
    public Booking save(Booking booking) {
        BookingEntity bookingEntity = bookingMapper.mapToEntity(booking);
        BookingEntity savedBookingEntity = bookingJpaRepository.save(bookingEntity);
        return bookingMapper.mapToModel(savedBookingEntity);
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingJpaRepository.findById(id)
                .map(bookingMapper::mapToModel);
    }

    @Override
    public Page<Booking> findAll(Pageable paging) {
        return bookingJpaRepository.findAll(paging)
                .map(bookingMapper::mapToModel);
    }

    @Override
    public List<Booking> findAllInMonthsOfStartAndEndDateAndInBetween(Set<Integer> yearsCoveringNewBookingDate,
                                                                      List<Integer> monthsCoveringNewBookingDate,
                                                                      int startYear,
                                                                      int endYear,
                                                                      int startMonth,
                                                                      int endMonth) {
        return bookingJpaRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsCoveringNewBookingDate,
                                                                               monthsCoveringNewBookingDate,
                                                                               startYear,
                                                                               endYear,
                                                                               startMonth,
                                                                               endMonth)
                .stream()
                .map(bookingMapper::mapToModel)
                .collect(Collectors.toList());
    }

}
