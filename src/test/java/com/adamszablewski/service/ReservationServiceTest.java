package com.adamszablewski.service;

import com.adamszablewski.reservations.ReservationRepository;
import com.adamszablewski.reservations.ReservationService;
import com.adamszablewski.reservations.RoomPrice;
import com.adamszablewski.rooms.RoomAvailabilityCheck;
import com.adamszablewski.rooms.RoomRepository;
import com.adamszablewski.rooms.RoomService;
import com.adamszablewski.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    ReservationRepository reservationRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    RoomService roomService;

    @Mock
    ReservationService reservationService;


    RoomPrice roomPrice;


    RoomAvailabilityCheck roomAvailabilityCheck;

    @Mock
    RoomRepository roomRepository;

    @BeforeEach
    void setUp(){
        roomRepository.deleteAll();
        reservationService = new ReservationService(reservationRepository, userRepository, roomService,
                                                         roomPrice, roomAvailabilityCheck, roomRepository);
    }

    @Test
    public void deleteReservationByIdTest() {
        // Arrange
        Long resId = 1L;
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);


        // Act
        ResponseEntity<String> response = reservationService.deleteReservationById(resId);

        // Assert
        verify(reservationRepository).deleteById(resId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Reservation cancelled");
    }


}
