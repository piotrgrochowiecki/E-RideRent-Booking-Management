package com.piotrgrochowiecki.eriderentbookingmanagement.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookingJpaRepository extends JpaRepository<BookingEntity, Long> {

    Page<BookingEntity> findAll(Pageable pageable);

    @Query("SELECT b FROM BookingEntity b WHERE " +
           "((FUNCTION('YEAR', b.startDate) IN :years AND FUNCTION('MONTH', b.startDate) IN :months) OR " +
           "(FUNCTION('YEAR', b.endDate) IN :years AND FUNCTION('MONTH', b.endDate) IN :months)) OR " +
           "((FUNCTION('YEAR', b.startDate) >= :startYear AND FUNCTION('MONTH', b.startDate) >= :startMonth) AND " +
           "(FUNCTION('YEAR', b.endDate) <= :endYear AND FUNCTION('MONTH', b.endDate) <= :endMonth))")
    List<BookingEntity> findAllBookingEntitiesWithinMonthsAndYears(@Param("years") Set<Integer> years,
                                                                   @Param("months") List<Integer> months,
                                                                   @Param("startYear") int startYear,
                                                                   @Param("endYear") int endYear,
                                                                   @Param("startMonth") int startMonth,
                                                                   @Param("endMonth") int endMonth);
}
