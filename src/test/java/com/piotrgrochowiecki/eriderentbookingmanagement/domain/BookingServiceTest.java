package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
    void test1() {
        //given
        LocalDate newBookingStartDate = LocalDate.of(2023, 10, 6);
        LocalDate newBookingEndDate = LocalDate.of(2023, 10, 18);

        Booking booking1 = Booking.builder()
                .startDate(LocalDate.of(2023, 9, 28))
                .endDate(LocalDate.of(2023, 10, 10))
                .build();
    }

}