package com.adamszablewski.service;

import com.adamszablewski.reservations.Reservation;
import com.adamszablewski.reservations.ReservationRepository;
import com.adamszablewski.reservations.ReservationService;
import com.adamszablewski.reservations.RoomPrice;
import com.adamszablewski.rooms.Room;
import com.adamszablewski.rooms.RoomService;
import com.adamszablewski.users.UserInfo;
import com.adamszablewski.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private RoomService roomService;

    @Mock
    private RoomPrice roomPrice;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testCreateReservationWithAvailableRooms() {
        // Arrange
        Reservation reservation = Reservation.builder()
                .username("adam@test.com")
                .guests(3)
                .arrival(LocalDate.of(2023, 6, 8))
                .departure(LocalDate.of(2023, 6, 14))
                .build();

        Room room1 = Room.builder()
                .roomCategory("Basic")
                .maxNumberOfGuests(2)
                .pricePerNight(200)
                .build();
        Room room2 = Room.builder()
                .roomCategory("Basic")
                .maxNumberOfGuests(2)
                .pricePerNight(200)
                .build();


        List<Room> availableRooms = new ArrayList<>();
        availableRooms.add(room1);
        availableRooms.add(room2);
        when(roomService.findAvailableRoomsForReservation(reservation)).thenReturn(availableRooms);
        when(roomPrice.calculateTotalRoomPrice(reservation)).thenReturn(0.0);
        UserInfo user = new UserInfo();
        user.setUsername("testuser");
        when(userRepository.findByUsername(reservation.getUsername())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).save(user);
        doNothing().when(reservationRepository).save(reservation);

        // Act
        ResponseEntity<String> response = reservationService.createReservation(reservation);

        // Assert
        verify(roomService, times(1)).findAvailableRoomsForReservation(reservation);
        verify(roomPrice, times(1)).calculateTotalRoomPrice(reservation);
        verify(userRepository, times(1)).findByUsername(reservation.getUsername());
        verify(userRepository, times(1)).save(user);
        verify(reservationRepository, times(2)).save(reservation);
        assertEquals(ResponseEntity.ok("Reservation created"), response);
    }

    @Test
    public void testCreateReservationWithNoAvailableRooms() {
        // Arrange
        Reservation reservation = Reservation.builder()
                .username("adam@test.com")
                .guests(3)
                .arrival(LocalDate.of(2023, 6, 8))
                .departure(LocalDate.of(2023, 6, 14))
                .build();

        List<Room> availableRooms = new ArrayList<>();
        when(roomService.findAvailableRoomsForReservation(reservation)).thenReturn(availableRooms);

        // Act
        ResponseEntity<String> response = reservationService.createReservation(reservation);

        // Assert
        verify(roomService, times(1)).findAvailableRoomsForReservation(reservation);
        verifyNoInteractions(roomPrice);
        verifyNoInteractions(userRepository);
        verifyNoInteractions(reservationRepository);
        assertEquals(ResponseEntity.ok("No available rooms in this category"), response);
    }
}

