package com.piotrgrochowiecki.eriderentbookingmanagement.data;

import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.BookingRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingMapperTest {

    private final BookingMapper bookingMapper;

    @Autowired
    public BookingMapperTest(BookingMapper bookingMapper) {
        this.bookingMapper = bookingMapper;
    }

    @Test
    void givenBooking_whenMapToEntity_thenReturnBookingEntity() {
        //given
        LocalDate startDate1 = LocalDate.of(2023, 9, 3);
        LocalDate endDate1 = LocalDate.of(2023, 9, 10);

        Booking booking1 = Booking.builder()
                .startDate(startDate1)
                .endDate(endDate1)
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(startDate1)
                .endDate(endDate1)
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        //when
        BookingEntity result = bookingMapper.mapToEntity(booking1);

        //then
        assertEquals(bookingEntity1, result);
    }

    @Test
    void givenBookingEntity_whenMapToModel_thenReturnBooking() {
        //given
        LocalDate startDate1 = LocalDate.of(2023, 9, 3);
        LocalDate endDate1 = LocalDate.of(2023, 9, 10);

        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(startDate1)
                .endDate(endDate1)
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        BookingEntity bookingEntity1 = BookingEntity.builder()
                .id(1L)
                .startDate(startDate1)
                .endDate(endDate1)
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        //when
        Booking result = bookingMapper.mapToModel(bookingEntity1);

        //then
        assertEquals(booking1, result);
    }
}