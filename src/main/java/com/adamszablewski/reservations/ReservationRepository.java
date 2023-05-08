package com.adamszablewski.reservations;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsById(Long id);
    List<Reservation> findByArrivalBetween(LocalDate startDate, LocalDate endDate);
}
