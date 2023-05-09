package com.adamszablewski.service;

import com.adamszablewski.reservations.Reservation;
import com.adamszablewski.reservations.ReservationRepository;
import com.adamszablewski.reservations.ReservationService;
import com.adamszablewski.reservations.RoomPrice;
import com.adamszablewski.rooms.Room;
import com.adamszablewski.rooms.RoomAvailabilityCheck;
import com.adamszablewski.rooms.RoomRepository;
import com.adamszablewski.rooms.RoomService;
import com.adamszablewski.users.UserInfo;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Mock
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
    public void findAllReservationsForUserByEmail_ShouldReturnAll(){
        String email = "test@test.com";
        UserInfo user = UserInfo.builder()
                .username(email)
                .phoneNumber("123456789")
                .name("Test")
                .lastName("Testing")
                .build();

        Reservation r1 = Reservation.builder()
                .user(user)
                .build();
        Reservation r2 = Reservation.builder()
                .user(user)
                .build();
        List<Reservation> reservations = new ArrayList<>();

        reservations.add(r1);
        reservations.add(r2);

        user.setReservations(reservations);
       when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));



       List<Reservation> result = reservationService.findAllReservationsForUserByEmail(user.getUsername());


       verify(userRepository).findByUsername(user.getUsername());
       assertThat(result).isEqualTo(user.getReservations());


    }

    @Test
    public void findAllReservationsForUserByPhoneNumber_ShouldReturnNone(){
        String phonenumber = "123456789";


        when(userRepository.findByPhoneNumber(phonenumber)).thenReturn(Optional.empty());



        List<Reservation> result = reservationService.findAllReservationsForUserByPhoneNumber(phonenumber);


        verify(userRepository).findByPhoneNumber(phonenumber);
        assertThat(result).isEmpty();
    }

    @Test
    public void findAllReservationsForUserByPhonenumber_ShouldReturnAll(){

        UserInfo user = UserInfo.builder()
                .phoneNumber("123456789")
                .build();

        Reservation r1 = Reservation.builder()
                .user(user)
                .build();
        Reservation r2 = Reservation.builder()
                .user(user)
                .build();
        List<Reservation> reservations = new ArrayList<>();

        reservations.add(r1);
        reservations.add(r2);
        user.setReservations(reservations);
        when(userRepository.findByPhoneNumber(user.getUsername())).thenReturn(Optional.of(user));



        List<Reservation> result = reservationService.findAllReservationsForUserByPhoneNumber(user.getUsername());


        verify(userRepository).findByPhoneNumber(user.getPhoneNumber());
        assertThat(result).isEqualTo(user.getReservations());
    }

    @Test
    public void findAllReservationsForUserByEmail_ShouldReturnNone(){
        String email = "test@test.com";


        when(userRepository.findByUsername(email)).thenReturn(Optional.empty());



        List<Reservation> result = reservationService.findAllReservationsForUserByEmail(email);


        verify(userRepository).findByUsername(email);
        assertThat(result).isEmpty();


    }

    @Test
    public void deleteReservationByIdTest_ReservationExists() {

        Long resId = 1L;
        when(reservationRepository.existsById(resId)).thenReturn(true);


        ResponseEntity<String> response = reservationService.deleteReservationById(resId);


        verify(reservationRepository).deleteById(resId);
        verify(reservationRepository).existsById(resId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Reservation cancelled");
    }

    @Test
    public void deleteReservationByIdTest_ReservationDoesNotExist() {

        Long resId = 1L;
        when(reservationRepository.existsById(resId)).thenReturn(false);


        ResponseEntity<String> response = reservationService.deleteReservationById(resId);

        verify(reservationRepository).existsById(resId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void createReservation_ShouldCreate(){
        Reservation reservation = Reservation.builder()
                .arrival(LocalDate.of(2023, 7, 8))
                .departure(LocalDate.of(2023, 7, 16))
                .username("testuser@test.com")
                .guests(2)
                .build();

        Room r1 = Room.builder()
                .maxNumberOfGuests(2)
                .pricePerNight(250)
                .build();

        UserInfo user = UserInfo.builder()
                        .username("testuser@test.com")
                .points(0)
                .build();

        double avgRoomPrice = 250;
        when(roomService.findAvailableRoomsForReservation(reservation)).thenReturn(List.of(r1));
        when(roomPrice.calculateAvgRoomPrice(List.of(r1))).thenReturn((double) r1.getPricePerNight());
        when(roomPrice.calculateTotalRoomPrice(reservation)).thenReturn((double) r1.getPricePerNight());
        when(userRepository.findByUsername(reservation.getUsername())).thenReturn(Optional.of(user));
        when(roomPrice.calculatePointsForUser(avgRoomPrice * reservation.getNumberOfNights(reservation))).thenReturn(10000.0);


        ResponseEntity<String> response = reservationService.createReservation(reservation);


        verify(reservationRepository, times(2)).save(reservation);
        verify(roomService).findAvailableRoomsForReservation(reservation);
        verify(roomPrice).calculateAvgRoomPrice(List.of(r1));
        verify(roomPrice).calculateTotalRoomPrice(reservation);
        verify(userRepository).findByUsername(reservation.getUsername());
        verify(roomPrice).calculatePointsForUser(avgRoomPrice * reservation.getNumberOfNights(reservation));
        verify(userRepository).save(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Reservation created");

    }
    @Test
    public void createReservation_ShouldNotCreateReservation(){
        Reservation reservation = Reservation.builder()
                .arrival(LocalDate.of(2023, 7, 8))
                .departure(LocalDate.of(2023, 7, 16))
                .username("testuser@test.com")
                .guests(2)
                .build();

        UserInfo user = UserInfo.builder()
                .username("testuser@test.com")
                .points(0)
                .build();
        List<Room> reservations = new ArrayList<>();
        when(roomService.findAvailableRoomsForReservation(reservation)).thenReturn(reservations);


        ResponseEntity<String> response = reservationService.createReservation(reservation);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("No available rooms in this category");

    }

    @Test
    public void updateReservationById_ShouldUpdate(){
        Reservation reservation = Reservation.builder()
                .id(1L)
                .arrival(LocalDate.of(2023, 7, 8))
                .departure(LocalDate.of(2023, 7, 16))
                .username("testuser@test.com")
                .totalPrice(200)
                .guests(2)
                .build();

        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
        when(roomPrice.calculateTotalRoomPrice(reservation)).thenReturn(100.00);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationService.updateReservationById(reservation.getId(), reservation);

        verify(reservationRepository).findById(1L);
        verify(roomPrice).calculateTotalRoomPrice(reservation);
        verify(reservationRepository).save(reservation);
        assertThat(response.getBody().getTotalPrice()).isEqualTo(reservation.getTotalPrice());
    }

    @Test
    public void updateReservationById_ShouldNotUpdate(){
        Reservation reservation = Reservation.builder()
                .id(1L)
                .arrival(LocalDate.of(2023, 7, 8))
                .departure(LocalDate.of(2023, 7, 16))
                .username("testuser@test.com")
                .totalPrice(200)
                .guests(2)
                .build();

        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.empty());

        ResponseEntity<Reservation> response = reservationService.updateReservationById(reservation.getId(), reservation);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }



}
