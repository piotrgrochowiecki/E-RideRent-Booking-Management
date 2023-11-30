package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking getById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new NotFoundRuntimeException(id));
    }

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }
    
    public List<Booking> getAllBookingsOverlappingWithDates(LocalDate newBookingStartDate, LocalDate newBookingEndDate) {
        List<Booking> allBookings = getAll();
        return allBookings.stream()
                .filter(booking -> isNewBookingOverlappingExistingBookings(newBookingStartDate, newBookingEndDate, booking))
                .collect(Collectors.toList());
    }
    
    private boolean isNewBookingOverlappingExistingBookings(LocalDate newBookingStartDate, LocalDate newBookingEndDate, Booking existingBooking) {
        return ((existingBooking.startDate().isBefore(newBookingEndDate) || existingBooking.startDate().isEqual(newBookingEndDate))
                && (existingBooking.endDate().isAfter(newBookingStartDate) || existingBooking.endDate().isEqual(newBookingStartDate)));
    }

}
