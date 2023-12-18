package com.piotrgrochowiecki.eriderentbookingmanagement.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingCRUDRepositoryTest {

    private final TestEntityManager testEntityManager;

    private final BookingCRUDRepository bookingCRUDRepository;

    @Autowired
    public BookingCRUDRepositoryTest(TestEntityManager testEntityManager, BookingCRUDRepository bookingCRUDRepository) {
        this.testEntityManager = testEntityManager;
        this.bookingCRUDRepository = bookingCRUDRepository;
    }

    @Test
    public void whenFindAll_thenReturnListOfTwoBookingEntities() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 10, 1))
                .endDate(LocalDate.of(2023, 10, 17))
                .carUuid("carUuid1")
                .userUuid("UserUuid1")
                .build();

        BookingEntity bookingEntity2 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 10, 8))
                .endDate(LocalDate.of(2023, 10, 21))
                .carUuid("carUuid2")
                .userUuid("UserUuid2")
                .build();

        testEntityManager.persist(bookingEntity1);
        testEntityManager.persist(bookingEntity2);
        testEntityManager.flush();

        //when
        List<BookingEntity> actual = bookingCRUDRepository.findAll();

        //then
        assertEquals(2, actual.size());
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in the same month, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the same month and year as parameters, " +
                 "then it should return list with one booking entity")
    void shouldReturnListWithOneEntity() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 11, 10))
                .endDate(LocalDate.of(2023, 11, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2023);
        List<Integer> monthsOfNewBookingDate = List.of(11);
        int startYear = 2023;
        int endYear = 2023;
        int startMonth = 11;
        int endMonth = 11;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.contains(bookingEntity1));
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in the same month, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the other month and the same year as parameters, " +
                 "then it should return empty list")
    void shouldReturnEmptyList() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 11, 10))
                .endDate(LocalDate.of(2023, 11, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2023);
        List<Integer> monthsOfNewBookingDate = List.of(10);
        int startYear = 2023;
        int endYear = 2023;
        int startMonth = 10;
        int endMonth = 10;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in the same month, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the same month and other year as parameters, " +
                 "then it should return empty list")
    void shouldReturnEmptyList2() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 11, 10))
                .endDate(LocalDate.of(2023, 11, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2024);
        List<Integer> monthsOfNewBookingDate = List.of(11);
        int startYear = 2024;
        int endYear = 2024;
        int startMonth = 11;
        int endMonth = 11;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in two consecutive months in one year, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the starting and ending month as ending month of existing booking and the same year as parameters, " +
                 "then it should return list with one entity")
    void shouldReturnListWithOneEntity2() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 11, 10))
                .endDate(LocalDate.of(2023, 12, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2023);
        List<Integer> monthsOfNewBookingDate = List.of(12);
        int startYear = 2023;
        int endYear = 2023;
        int startMonth = 12;
        int endMonth = 12;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.contains(bookingEntity1));
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in two consecutive months in one year, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the starting and ending month as starting month of existing booking and the same year as parameters, " +
                 "then it should return list with one entity")
    void shouldReturnListWithOneEntity3() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 11, 10))
                .endDate(LocalDate.of(2023, 12, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2023);
        List<Integer> monthsOfNewBookingDate = List.of(11);
        int startYear = 2023;
        int endYear = 2023;
        int startMonth = 11;
        int endMonth = 11;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.contains(bookingEntity1));
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in two consecutive months spanning over two calendar years, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the starting and ending month as starting month of existing booking and the first year as parameters, " +
                 "then it should return list with one entity")
    void shouldReturnListWithOneEntity4() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 12, 10))
                .endDate(LocalDate.of(2024, 1, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2023);
        List<Integer> monthsOfNewBookingDate = List.of(12);
        int startYear = 2023;
        int endYear = 2023;
        int startMonth = 12;
        int endMonth = 12;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.contains(bookingEntity1));
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in two consecutive months spanning over two calendar years, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the same starting and ending months and years as parameters, " +
                 "then it should return list with one entity")
    void shouldReturnListWithOneEntity5() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 12, 10))
                .endDate(LocalDate.of(2024, 1, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2023, 2024);
        List<Integer> monthsOfNewBookingDate = List.of(12, 1);
        int startYear = 2023;
        int endYear = 2024;
        int startMonth = 12;
        int endMonth = 1;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.contains(bookingEntity1));
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in two consecutive months spanning over two calendar years, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the starting month and year as ending of existing booking and ending next month as parameters, " +
                 "then it should return list with one entity")
    void shouldReturnListWithOneEntity6() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 12, 10))
                .endDate(LocalDate.of(2024, 1, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2024);
        List<Integer> monthsOfNewBookingDate = List.of(1, 2);
        int startYear = 2024;
        int endYear = 2024;
        int startMonth = 1;
        int endMonth = 2;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.contains(bookingEntity1));
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in two consecutive months spanning over two calendar years, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the ending month and year as starting of existing booking as parameters, " +
                 "then it should return list with one entity")
    void shouldReturnListWithOneEntity7() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 12, 10))
                .endDate(LocalDate.of(2024, 1, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2023);
        List<Integer> monthsOfNewBookingDate = List.of(10, 11, 12);
        int startYear = 2023;
        int endYear = 2023;
        int startMonth = 10;
        int endMonth = 12;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.contains(bookingEntity1));
    }

    @Test
    @DisplayName("Given one booking in DB starting and ending in two consecutive months spanning over two calendar years, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the starting month and year before starting date of existing booking and ending after ending of existing booking as parameters, " +
                 "then it should return list with one entity")
    void shouldReturnListWithOneEntity8() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 12, 10))
                .endDate(LocalDate.of(2024, 1, 17))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2023, 2024);
        List<Integer> monthsOfNewBookingDate = List.of(10, 11, 12, 1, 2);
        int startYear = 2023;
        int endYear = 2024;
        int startMonth = 10;
        int endMonth = 2;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.contains(bookingEntity1));
    }

    @Test
    @DisplayName("Given two bookings in DB, one starting and ending in two consecutive months spanning over two calendar years and one spanning over two months of the same year, " +
                 "when findAllBookingEntitiesWithinMonthsAndYears is invoked with the starting month and year before starting date of first existing booking and ending in the same month as start of other existing booking as parameters, " +
                 "then it should return list with two entities")
    void shouldReturnListWithTwoEntities() {
        //given
        BookingEntity bookingEntity1 = BookingEntity.builder()
                .startDate(LocalDate.of(2023, 12, 10))
                .endDate(LocalDate.of(2024, 1, 17))
                .build();

        BookingEntity bookingEntity2 = BookingEntity.builder()
                .startDate(LocalDate.of(2024, 2, 18))
                .endDate(LocalDate.of(2024, 3, 4))
                .build();
        testEntityManager.persist(bookingEntity1);
        testEntityManager.persist(bookingEntity2);
        testEntityManager.flush();

        //when
        Set<Integer> yearsOfNewBookingDate = Set.of(2023, 2024);
        List<Integer> monthsOfNewBookingDate = List.of(10, 11, 12, 1, 2);
        int startYear = 2023;
        int endYear = 2024;
        int startMonth = 10;
        int endMonth = 2;

        List<BookingEntity> result = bookingCRUDRepository.findAllBookingEntitiesWithinMonthsAndYears(yearsOfNewBookingDate,
                                                                                                      monthsOfNewBookingDate,
                                                                                                      startYear,
                                                                                                      endYear,
                                                                                                      startMonth,
                                                                                                      endMonth);

        //then
        assertTrue(result.contains(bookingEntity1));
        assertTrue(result.contains(bookingEntity2));
    }

}