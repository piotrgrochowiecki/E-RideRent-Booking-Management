package com.piotrgrochowiecki.eriderentbookingmanagement.data;

import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@AllArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingCRUDRepository bookingCRUDRepository;
    private final BookingMapper bookingMapper;

    @Override
    public Booking save(@Nullable Booking booking) {
        assert booking != null;
        BookingEntity bookingEntity = bookingMapper.mapToEntity(booking);
        BookingEntity savedBookingEntity = bookingCRUDRepository.save(bookingEntity);
        return bookingMapper.mapToModel(savedBookingEntity);
    }

    @Override
    public Optional<Booking> findById(@Nullable Long id) {
        assert id != null;
        return bookingCRUDRepository.findById(id)
                .map(bookingMapper::mapToModel);
    }

    @Override
    public List<Booking> findAll() {
        return bookingCRUDRepository.findAll()
                .stream()
                .map(bookingMapper::mapToModel)
                .collect(Collectors.toList());
    }

}
