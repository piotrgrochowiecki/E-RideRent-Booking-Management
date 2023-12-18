package com.piotrgrochowiecki.eriderentbookingmanagement.remote.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.BookingService;
import com.piotrgrochowiecki.eriderentbookingmanagement.domain.NotFoundRuntimeException;
import com.piotrgrochowiecki.eriderentbookingmanagement.remote.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentbookingmanagement.remote.mapper.BookingApiMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    public BookingControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @MockBean
    private BookingService bookingService;

    @MockBean
    private BookingApiMapper bookingApiMapper;

    @Test
    void shouldReturnOkStatusAndBookingResponseDto() throws Exception {
        //given
        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 9, 28))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        BookingResponseDto bookingResponseDto = BookingResponseDto.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 9, 28))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        when(bookingService.getById(1L))
                .thenReturn(booking1);

        when(bookingApiMapper.mapToDto(booking1))
                .thenReturn(bookingResponseDto);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String bookingResponseDtoAsJson = gson.toJson(bookingResponseDto);

        //when and then
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/v1/internal/booking/id/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                                   .isOk())
                .andExpect(content()
                                   .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                                   .json(bookingResponseDtoAsJson));
    }

    @Test
    void shouldReturnNotFoundStatus() throws Exception {
        //given
        NotFoundRuntimeException notFoundRuntimeException = new NotFoundRuntimeException(1L);

        when(bookingService.getById(1L))
                .thenThrow(notFoundRuntimeException);
        //when and then
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/v1/internal/booking/id/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                                   .isNotFound())
                .andExpect(content()
                                   .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnBadRequestStatus() throws Exception {
        //when and then
        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/v1/internal/booking/id/" + id)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                                   .isBadRequest());
    }

    @Test
    void shouldReturnOkStatusAndListOfTwoBookingResponseDtos() throws Exception {
        //given
        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 9, 28))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        BookingResponseDto bookingResponseDto1 = BookingResponseDto.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 9, 28))
                .endDate(LocalDate.of(2023, 10, 10))
                .userUuid("userUuid1")
                .carUuid("carUuid1")
                .build();

        when(bookingApiMapper.mapToDto(booking1))
                .thenReturn(bookingResponseDto1);

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 11, 10))
                .endDate(LocalDate.of(2023, 11, 12))
                .userUuid("userUuid2")
                .carUuid("carUuid2")
                .build();

        BookingResponseDto bookingResponseDto2 = BookingResponseDto.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 11, 10))
                .endDate(LocalDate.of(2023, 11, 12))
                .userUuid("userUuid2")
                .carUuid("carUuid2")
                .build();

        List<Booking> bookingList = List.of(booking1,
                                            booking2);

        List<BookingResponseDto> bookingResponseDtos = List.of(bookingResponseDto1,
                                                               bookingResponseDto2);

        when(bookingApiMapper.mapToDto(booking2))
                .thenReturn(bookingResponseDto2);

        when(bookingService.getAll())
                .thenReturn(bookingList);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String bookingResponseDtoListAsJson = gson.toJson(bookingResponseDtos);


        //when and then
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/v1/internal/booking/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                                   .isOk())
                .andExpect(content()
                                   .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                                   .json(bookingResponseDtoListAsJson));
    }

}