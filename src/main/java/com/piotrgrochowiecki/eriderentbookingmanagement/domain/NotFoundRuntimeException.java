package com.piotrgrochowiecki.eriderentbookingmanagement.domain;

public class NotFoundRuntimeException extends RuntimeException {

    public NotFoundRuntimeException(Long id) {
        super("Booking with id " + id + " has not been found");
    }

}
