package com.piotrgrochowiecki.eriderentbookingmanagement.remote.controller;

import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.RuntimeExceptionDto;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.NotFoundRuntimeException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundRuntimeException.class)
    public RuntimeExceptionDto handleNotFoundRuntimeException(NotFoundRuntimeException exception) {
        return RuntimeExceptionDto.builder()
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public RuntimeExceptionDto handleConstraintValidationException(ConstraintViolationException exception) {
        return RuntimeExceptionDto.builder()
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RuntimeExceptionDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return RuntimeExceptionDto.builder()
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

}
