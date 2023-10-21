package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository {

    Booking save(@Nullable Booking booking);

    Optional<Booking> findById(@Nullable Long id);

    List<Booking> findAll();

}
