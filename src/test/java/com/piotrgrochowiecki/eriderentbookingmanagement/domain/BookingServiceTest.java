package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookingServiceTest {

    private final BookingService bookingService;

    @Autowired
    public BookingServiceTest(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @MockBean
    private BookingRepository bookingRepository;

    @Test
    @DisplayName("When getById is invoked" +
                 "then return one booking object")
    void shouldReturnOneBooking() {
        //given
        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 9, 28))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking1));

        //when
        Booking result = bookingService.getById(1L);

        //then
        assertEquals(result, booking1);
    }

    @Test
    @DisplayName("When getById is invoked with an id that does not exist" +
                 "then throw NotFoundRuntimeException")
    void shouldThrowNotFoundException() {
        //given
        when(bookingRepository.findById(2L))
                .thenReturn(Optional.empty());

        //when and then
        assertThrows(NotFoundRuntimeException.class, () -> bookingService.getById(2L));
    }

    @Test
    @DisplayName("Given 2 booking objects, " +
                 "when findAll is invoked, " +
                 "then return list of two Booking objects")
    void shouldReturnListOfTwoBookingObjects() {
        //given
        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 9, 28))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 10, 12))
                .endDate(LocalDate.of(2023, 10, 18))
                .userUuid("userUuid2")
                .carUuid("carUuid2")
                .build();

        List<Booking> bookingList = List.of(booking1,
                                            booking2);
        Page<Booking> bookingPage = new PageImpl<>(bookingList);
        Pageable paging = PageRequest.of(1, 10, Sort.by("id"));

        when(bookingRepository.findAll(paging))
                .thenReturn(bookingPage);

        //when
        Page<Booking> resultPage = bookingService.getAll(1, 10, "id");
        List<Booking> result = resultPage.stream()
                .toList();

        //then
        assertTrue(result.contains(booking1));
        assertTrue(result.contains(booking2));
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Given new booking request dates overlapping with one of two pre-existing bookings, " +
                 "when getAllBookingsOverlappingWithDates is invoked, " +
                 "then return list with one booking")
    void shouldReturnListOfOneBooking() {
        //given
        LocalDate newBookingStartDate = LocalDate.of(2023, 10, 5);
        LocalDate newBookingEndDate = LocalDate.of(2023, 10, 15);

        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 9, 28))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 10, 17))
                .endDate(LocalDate.of(2023, 10, 25))
                .userUuid("userUuid2")
                .carUuid("carUuid2")
                .build();

        List<Booking> bookingList = List.of(booking1,
                                            booking2);

        when(bookingRepository.findAllInMonthsOfStartAndEndDateAndInBetween(Set.of(2023),
                                                                            List.of(10),
                                                                            newBookingStartDate.getYear(),
                                                                            newBookingEndDate.getYear(),
                                                                            newBookingStartDate.getMonthValue(),
                                                                            newBookingEndDate.getMonthValue()))
                .thenReturn(bookingList);

        //when
        List<Booking> result = bookingService.getAllBookingsOverlappingWithDates(newBookingStartDate, newBookingEndDate);

        //then
        assertTrue(result.contains(booking1));
        assertFalse(result.contains(booking2));
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Given new booking request start date overlapping with end date of one of two pre-existing bookings, " +
                 "when getAllBookingsOverlappingWithDates is invoked, " +
                 "then return list with one booking")
    void shouldReturnListOfOneBooking2() {
        //given
        LocalDate newBookingStartDate = LocalDate.of(2023, 10, 10);
        LocalDate newBookingEndDate = LocalDate.of(2023, 10, 15);

        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 9, 28))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 10, 17))
                .endDate(LocalDate.of(2023, 10, 25))
                .userUuid("userUuid2")
                .carUuid("carUuid2")
                .build();

        List<Booking> bookingList = List.of(booking1,
                                            booking2);

        when(bookingRepository.findAllInMonthsOfStartAndEndDateAndInBetween(Set.of(2023),
                                                                            List.of(10),
                                                                            newBookingStartDate.getYear(),
                                                                            newBookingEndDate.getYear(),
                                                                            newBookingStartDate.getMonthValue(),
                                                                            newBookingEndDate.getMonthValue()))
                .thenReturn(bookingList);

        //when
        List<Booking> result = bookingService.getAllBookingsOverlappingWithDates(newBookingStartDate, newBookingEndDate);

        //then
        assertTrue(result.contains(booking1));
        assertFalse(result.contains(booking2));
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Given new booking request start date overlapping with start date of one of two pre-existing bookings, " +
                 "when getAllBookingsOverlappingWithDates is invoked, " +
                 "then return list with one booking")
    void shouldReturnListOfOneBooking3() {
        //given
        LocalDate newBookingStartDate = LocalDate.of(2023, 10, 4);
        LocalDate newBookingEndDate = LocalDate.of(2023, 10, 15);

        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 10, 4))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 10, 17))
                .endDate(LocalDate.of(2023, 10, 25))
                .userUuid("userUuid2")
                .carUuid("carUuid2")
                .build();

        List<Booking> bookingList = List.of(booking1,
                                            booking2);

        when(bookingRepository.findAllInMonthsOfStartAndEndDateAndInBetween(Set.of(2023),
                                                                            List.of(10),
                                                                            newBookingStartDate.getYear(),
                                                                            newBookingEndDate.getYear(),
                                                                            newBookingStartDate.getMonthValue(),
                                                                            newBookingEndDate.getMonthValue()))
                .thenReturn(bookingList);

        //when
        List<Booking> result = bookingService.getAllBookingsOverlappingWithDates(newBookingStartDate, newBookingEndDate);

        //then
        assertTrue(result.contains(booking1));
        assertFalse(result.contains(booking2));
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Given new booking request dates is not overlapping with any of two pre-existing bookings, " +
                 "when getAllBookingsOverlappingWithDates is invoked, " +
                 "then return empty list")
    void shouldReturnEmptyList() {
        //given
        LocalDate newBookingStartDate = LocalDate.of(2023, 10, 11);
        LocalDate newBookingEndDate = LocalDate.of(2023, 10, 16);

        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 10, 2))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 10, 17))
                .endDate(LocalDate.of(2023, 10, 25))
                .userUuid("userUuid2")
                .carUuid("carUuid2")
                .build();

        List<Booking> bookingList = List.of(booking1,
                                            booking2);

        when(bookingRepository.findAllInMonthsOfStartAndEndDateAndInBetween(Set.of(2023),
                                                                            List.of(10),
                                                                            newBookingStartDate.getYear(),
                                                                            newBookingEndDate.getYear(),
                                                                            newBookingStartDate.getMonthValue(),
                                                                            newBookingEndDate.getMonthValue()))
                .thenReturn(bookingList);

        //when
        List<Booking> result = bookingService.getAllBookingsOverlappingWithDates(newBookingStartDate, newBookingEndDate);

        //then
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Given new booking request end date is overlapping with start date of one of two pre-existing bookings, " +
                 "when getAllBookingsOverlappingWithDates is invoked, " +
                 "then return list with one booking")
    void shouldReturnListOfOneBooking4() {
        //given
        LocalDate newBookingStartDate = LocalDate.of(2023, 10, 11);
        LocalDate newBookingEndDate = LocalDate.of(2023, 10, 15);

        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 10, 4))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 10, 15))
                .endDate(LocalDate.of(2023, 10, 25))
                .userUuid("userUuid2")
                .carUuid("carUuid2")
                .build();

        List<Booking> bookingList = List.of(booking1,
                                            booking2);

        when(bookingRepository.findAllInMonthsOfStartAndEndDateAndInBetween(Set.of(2023),
                                                                            List.of(10),
                                                                            newBookingStartDate.getYear(),
                                                                            newBookingEndDate.getYear(),
                                                                            newBookingStartDate.getMonthValue(),
                                                                            newBookingEndDate.getMonthValue()))
                .thenReturn(bookingList);

        //when
        List<Booking> result = bookingService.getAllBookingsOverlappingWithDates(newBookingStartDate, newBookingEndDate);

        //then
        assertFalse(result.contains(booking1));
        assertTrue(result.contains(booking2));
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Given new booking request is overlapping with two pre-existing bookings, " +
                 "when getAllBookingsOverlappingWithDates is invoked, " +
                 "then return list with one booking")
    void shouldReturnListOfTwoBooking() {
        //given
        LocalDate newBookingStartDate = LocalDate.of(2023, 9, 28);
        LocalDate newBookingEndDate = LocalDate.of(2023, 10, 18);

        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 10, 4))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 10, 15))
                .endDate(LocalDate.of(2023, 10, 25))
                .userUuid("userUuid2")
                .carUuid("carUuid2")
                .build();

        List<Booking> bookingList = List.of(booking1,
                                            booking2);

        when(bookingRepository.findAllInMonthsOfStartAndEndDateAndInBetween(Set.of(2023),
                                                                            List.of(9, 10),
                                                                            newBookingStartDate.getYear(),
                                                                            newBookingEndDate.getYear(),
                                                                            newBookingStartDate.getMonthValue(),
                                                                            newBookingEndDate.getMonthValue()))
                .thenReturn(bookingList);

        //when
        List<Booking> result = bookingService.getAllBookingsOverlappingWithDates(newBookingStartDate, newBookingEndDate);

        //then
        assertTrue(result.contains(booking1));
        assertTrue(result.contains(booking2));
        assertEquals(2, result.size());
    }

}