package com.piotrgrochowiecki.eriderentbookingmanagement.remote.mapper;

import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingRequestDto;
import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingApiMapperTest {

    private final BookingApiMapper bookingApiMapper;

    @Autowired

    public BookingApiMapperTest(BookingApiMapper bookingApiMapper) {
        this.bookingApiMapper = bookingApiMapper;
    }

    @Test
    @DisplayName("Given Booking object, " +
                 "when mapToDto is invoked, " +
                 "then it should return BookingResponseDto object")
    void shouldReturnBookingResponseDto() {
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

        BookingResponseDto bookingResponseDto1 = BookingResponseDto.builder()
                .id(1L)
                .startDate(startDate1)
                .endDate(endDate1)
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        //when
        BookingResponseDto result = bookingApiMapper.mapToDto(booking1);

        //then
        assertEquals(bookingResponseDto1, result);
    }

    @Test
    @DisplayName("Given BookingRequestDto object, " +
                 "when mapToModel is invoked, " +
                 "then it should return Booking object")
    void shouldReturnBooking() {
        //given
        LocalDate startDate1 = LocalDate.of(2023, 9, 3);
        LocalDate endDate1 = LocalDate.of(2023, 9, 10);

        Booking booking1 = Booking.builder()
                .startDate(startDate1)
                .endDate(endDate1)
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        BookingRequestDto bookingRequestDto1 = BookingRequestDto.builder()
                .startDate(startDate1)
                .endDate(endDate1)
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        //when
        Booking result = bookingApiMapper.mapToModel(bookingRequestDto1);

        //then
        assertEquals(booking1, result);
    }

}