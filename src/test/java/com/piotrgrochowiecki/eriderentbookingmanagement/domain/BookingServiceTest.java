package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
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
                 "it should return one booking object")
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
                 "it should throw NotFoundRuntimeException")
    void shouldThrowNotFoundException() {
        //given
        when(bookingRepository.findById(2L))
                .thenReturn(Optional.empty());

        //when and then
        assertThrows(NotFoundRuntimeException.class, () -> bookingService.getById(2L));
    }



}