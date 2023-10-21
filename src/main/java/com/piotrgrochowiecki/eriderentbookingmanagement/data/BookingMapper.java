package com.piotrgrochowiecki.eriderentbookingmanagement.data;

import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingEntity mapToEntity(Booking booking) {
        return BookingEntity.builder()
                .startDate(booking.stratDate())
                .endDate(booking.endDate())
                .userUuid(booking.userUuid())
                .carUuid(booking.carUuid())
                .build();
    }

    public Booking mapToModel(BookingEntity bookingEntity) {
        return Booking.builder()
                .id(bookingEntity.getId())
                .stratDate(bookingEntity.getStartDate())
                .endDate(bookingEntity.getEndDate())
                .userUuid(bookingEntity.getUserUuid())
                .carUuid(bookingEntity.getCarUuid())
                .build();
    }

}
