package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

        when(bookingRepository.findAll())
                .thenReturn(bookingList);

        //when
        List<Booking> result = bookingService.getAll();

        //then
        assertTrue(result.contains(booking1));
        assertTrue(result.contains(booking2));
        assertEquals(2, result.size());
    }

}