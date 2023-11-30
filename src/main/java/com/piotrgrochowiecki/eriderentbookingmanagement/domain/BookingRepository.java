package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository {

    Booking save(Booking booking);

    Optional<Booking> findById(Long id);

    List<Booking> findAll();

}
