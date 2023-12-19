package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking getById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new NotFoundRuntimeException(id));
    }

    public Page<Booking> getAll(Integer pageNumber, Integer pageSize, String propertyToSortBy) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(propertyToSortBy));
        return bookingRepository.findAll(paging);
    }
    
    public List<Booking> getAllBookingsOverlappingWithDates(LocalDate newBookingStartDate, LocalDate newBookingEndDate) {
        List<Booking> allBookingsInMonthsBetweenStartAndEndDates = getAllInMonthsAndYearsOfStartAndEndDateAndInBetween(newBookingStartDate, newBookingEndDate);
        return allBookingsInMonthsBetweenStartAndEndDates.stream()
                .filter(booking -> isNewBookingOverlappingExistingBookings(newBookingStartDate, newBookingEndDate, booking))
                .collect(Collectors.toList());
    }

    private List<Booking> getAllInMonthsAndYearsOfStartAndEndDateAndInBetween(LocalDate startDate, LocalDate endDate) {
        Set<Integer> yearsCoveringNewBookingDate = IntStream.rangeClosed(startDate.getYear(), endDate.getYear())
                .boxed()
                .collect(Collectors.toSet());
        List<Integer> monthsCoveringNewBookingDate = new ArrayList<>();

        if (startDate.getYear() == endDate.getYear()) {
            monthsCoveringNewBookingDate.addAll(IntStream.rangeClosed(startDate.getMonthValue(), endDate.getMonthValue())
                                                        .boxed()
                                                        .toList());
        } else {
            monthsCoveringNewBookingDate.addAll(IntStream.rangeClosed(startDate.getMonthValue(), 12)
                                                        .boxed()
                                                        .toList());
            monthsCoveringNewBookingDate.addAll(IntStream.rangeClosed(1, endDate.getMonthValue())
                                                        .boxed()
                                                        .toList());
        }

        return bookingRepository.findAllInMonthsOfStartAndEndDateAndInBetween(yearsCoveringNewBookingDate,
                                                                              monthsCoveringNewBookingDate,
                                                                              startDate.getYear(),
                                                                              endDate.getYear(),
                                                                              startDate.getMonthValue(),
                                                                              endDate.getMonthValue());
    }
    
    private boolean isNewBookingOverlappingExistingBookings(LocalDate newBookingStartDate, LocalDate newBookingEndDate, Booking existingBooking) {
        return ((existingBooking.startDate().isBefore(newBookingEndDate) || existingBooking.startDate().isEqual(newBookingEndDate))
                && (existingBooking.endDate().isAfter(newBookingStartDate) || existingBooking.endDate().isEqual(newBookingStartDate)));
    }

}
