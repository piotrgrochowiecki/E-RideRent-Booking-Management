package com.piotrgrochowiecki.eriderentbookingmanagement.data;

import com.piotrgrochowiecki.eriderentbookingmanagement.domain.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingRepositoryImplTest {

    private final BookingRepositoryImpl bookingRepository;

    @Autowired
    public BookingRepositoryImplTest(BookingRepositoryImpl bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @MockBean
    private BookingJpaRepository bookingJpaRepository;

    @MockBean
    private BookingMapper bookingMapper;

    @Test
    void givenBookingObject_whenSave_shouldReturnTheSameObject() {
        //given
        LocalDate startDate = LocalDate.of(2023, 10, 4);
        LocalDate endDate = LocalDate.of(2023, 10, 11);

        Booking booking = Booking.builder()
                .startDate(startDate)
                .endDate(endDate)
                .carUuid("carUuid")
                .userUuid("userUuid")
                .build();

        BookingEntity bookingEntity = BookingEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .carUuid("carUuid")
                .userUuid("userUuid")
                .build();

        when(bookingMapper.mapToEntity(booking))
                .thenReturn(bookingEntity);
        when(bookingJpaRepository.save(bookingEntity))
                .thenReturn(bookingEntity);
        when(bookingMapper.mapToModel(bookingEntity))
                .thenReturn(booking);

        //when
        Booking result = bookingRepository.save(booking);

        //then
        assertEquals(result.startDate(), booking.startDate());
        assertEquals(result.endDate(), booking.endDate());
        assertEquals(result.carUuid(), booking.carUuid());
        assertEquals(result.userUuid(), booking.userUuid());
    }

    @Test
    void givenId_whenFindById_thenReturnOptionalOfBooking() {
        //given
        Long id = 1L;

        LocalDate startDate = LocalDate.of(2023, 10, 4);
        LocalDate endDate = LocalDate.of(2023, 10, 11);

        Booking booking = Booking.builder()
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .carUuid("carUuid")
                .userUuid("userUuid")
                .build();

        BookingEntity bookingEntity = BookingEntity.builder()
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .carUuid("carUuid")
                .userUuid("userUuid")
                .build();

        when(bookingJpaRepository.findById(id))
                .thenReturn(Optional.of(bookingEntity));
        when(bookingMapper.mapToModel(bookingEntity))
                .thenReturn(booking);

        //when
        Optional<Booking> result = bookingRepository.findById(id);

        //then
        assertEquals(result, Optional.of(booking));
    }

    @Test
    void whenFindAll_thenReturnListOfTwoBookings() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 10, 4))
                .endDate(LocalDate.of(2023, 10, 11))
                .carUuid("carUuid1")
                .userUuid("userUuid1")
                .build();

        BookingEntity bookingEntity2 = BookingEntity.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 11, 4))
                .endDate(LocalDate.of(2023, 11, 11))
                .carUuid("carUuid2")
                .userUuid("userUuid2")
                .build();

        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 10, 4))
                .endDate(LocalDate.of(2023, 10, 11))
                .carUuid("carUuid1")
                .userUuid("userUuid1")
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 11, 4))
                .endDate(LocalDate.of(2023, 11, 11))
                .carUuid("carUuid2")
                .userUuid("userUuid2")
                .build();

        when(bookingMapper.mapToModel(bookingEntity1))
                .thenReturn(booking1);
        when(bookingMapper.mapToModel(bookingEntity2))
                .thenReturn(booking2);
        List<BookingEntity> bookingEntityList = List.of(bookingEntity1,
                                                        bookingEntity2);

        Pageable paging = PageRequest.of(0, 10, Sort.by("id"));
        Page<BookingEntity> bookingEntityPage = new PageImpl<>(bookingEntityList);
        when(bookingJpaRepository.findAll(paging))
                .thenReturn(bookingEntityPage);


        //when
        Page<Booking> resultPage = bookingRepository.findAll(paging);
        List<Booking> resultList = resultPage.stream().toList();

        //then
        assertTrue(resultList.contains(booking1));
        assertTrue(resultList.contains(booking2));
        assertEquals(2, resultList.size());
    }

}