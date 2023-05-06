package com.adamszablewski.repositories;

import com.adamszablewski.reservations.Reservation;
import com.adamszablewski.reservations.ReservationRepository;
import com.adamszablewski.rooms.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @BeforeEach
    public void clearDbOfReservations(){
        reservationRepository.deleteAll();
    }

    @Test
    public void shouldReturnAllReservationsForPeriod(){


        Reservation reservation = Reservation.builder()
                .username("testuser1@test.com")
                .guests(3)
                .arrival(LocalDate.of(2023, 6, 8))
                .departure(LocalDate.of(2023, 6, 14))
                .build();

        Reservation reservation2 = Reservation.builder()
                .username("testuser2@test.com")
                .guests(3)
                .arrival(LocalDate.of(2023, 6, 15))
                .departure(LocalDate.of(2023, 6, 20))
                .build();

        Reservation reservation3 = Reservation.builder()
                .username("testuser3@test.com")
                .guests(3)
                .arrival(LocalDate.of(2023, 8, 8))
                .departure(LocalDate.of(2023, 8, 14))
                .build();

        reservationRepository.save(reservation);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);

        List<Reservation> reservationsInPeriod =
                reservationRepository.findByArrivalBetween(LocalDate.of(2023, 6,1),
                                                            LocalDate.of(2023, 6,30));

        assertThat(reservationsInPeriod.size()).isEqualTo(2);
    }



}
