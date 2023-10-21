package com.piotrgrochowiecki.eriderentbookingmanagement.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RuntimeExceptionDto(String message,
                                  LocalDateTime timeStamp) {

}
