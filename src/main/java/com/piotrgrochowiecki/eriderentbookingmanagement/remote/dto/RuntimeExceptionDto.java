package com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RuntimeExceptionDto(String message,
                                  LocalDateTime timeStamp) {

}
