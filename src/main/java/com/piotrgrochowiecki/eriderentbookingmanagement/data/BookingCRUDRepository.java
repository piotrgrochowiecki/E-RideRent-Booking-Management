package com.piotrgrochowiecki.eriderentbookingmanagement.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingCRUDRepository extends CrudRepository<BookingEntity, Long> {

    List<BookingEntity> findAll();


}
