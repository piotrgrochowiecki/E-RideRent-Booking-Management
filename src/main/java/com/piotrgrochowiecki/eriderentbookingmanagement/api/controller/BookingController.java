package com.piotrgrochowiecki.eriderentbookingmanagement.api.controller;

import com.piotrgrochowiecki.eriderentbookingmanagement.api.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentbookingmanagement.api.mapper.BookingApiMapper;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/internal/booking/")
public class BookingController {

    private final BookingService bookingService;
    private final BookingApiMapper bookingApiMapper;

    @GetMapping("{id}")
    public BookingResponseDto getById(@PathVariable @Nullable Long id) {
        Booking booking = bookingService.getById(id);
        return bookingApiMapper.mapToDto(booking);
    }

    @GetMapping("all")
    public List<BookingResponseDto> getAll() {
        return bookingService.getAll().stream()
                .map(bookingApiMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("all-overlapping-with-dates/{startDate}/{endDate}")
    public List<BookingResponseDto> getAllBookingsOverlappingWithDates(@PathVariable @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                       @PathVariable @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return bookingService.getAllBookingsOverlappingWithDates(startDate, endDate).stream()
                .map(bookingApiMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
