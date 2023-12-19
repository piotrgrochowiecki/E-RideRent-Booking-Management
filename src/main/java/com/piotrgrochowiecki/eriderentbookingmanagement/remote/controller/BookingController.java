package com.piotrgrochowiecki.eriderentbookingmanagement.remote.controller;

import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingRequestDto;
import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentbookingmanagement.remote.mapper.BookingApiMapper;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/internal/booking")
@Validated
public class BookingController {

    private final BookingService bookingService;
    private final BookingApiMapper bookingApiMapper;

    @GetMapping("id/{id}")
    public BookingResponseDto getById(@PathVariable("id") @NotNull Long id) {
        Booking booking = bookingService.getById(id);
        return bookingApiMapper.mapToDto(booking);
    }

    @GetMapping("all")
    public Page<BookingResponseDto> getAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "id") String propertyToSortBy) {

        return bookingService.getAll(pageNumber, pageSize, propertyToSortBy)
                .map(bookingApiMapper::mapToDto);
    }

    @GetMapping("all-overlapping-with-dates/{startDate}/{endDate}")
    public List<BookingResponseDto> getAllBookingsOverlappingWithDates(@PathVariable("startDate") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                       @PathVariable("endDate") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return bookingService.getAllBookingsOverlappingWithDates(startDate, endDate)
                .stream()
                .map(bookingApiMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<BookingResponseDto> save(@Valid @RequestBody BookingRequestDto bookingRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(fieldError -> System.out.println(fieldError.getField() + ": " + fieldError.getDefaultMessage() + "\n"));
        }
        Booking bookingToBeAdded = bookingApiMapper.mapToModel(bookingRequestDto);
        Booking addedBooking = bookingService.add(bookingToBeAdded);
        return new ResponseEntity<>(bookingApiMapper.mapToDto(addedBooking), HttpStatus.CREATED);
    }

}
