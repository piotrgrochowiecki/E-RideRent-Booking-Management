package com.piotrgrochowiecki.eriderentbookingmanagement.remote.mapper;

import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingRequestDto;
import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingApiMapper {

    public BookingResponseDto mapToDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.id())
                .startDate(booking.startDate())
                .endDate(booking.endDate())
                .userUuid(booking.userUuid())
                .carUuid(booking.carUuid())
                .build();
    }

    public Booking mapToModel(BookingRequestDto bookingRequestDto) {
        return Booking.builder()
                .startDate(bookingRequestDto.startDate())
                .endDate(bookingRequestDto.endDate())
                .userUuid(bookingRequestDto.userUuid())
                .carUuid(bookingRequestDto.carUuid())
                .build();
    }

}
