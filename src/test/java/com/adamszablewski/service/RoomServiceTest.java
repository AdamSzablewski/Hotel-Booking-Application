package com.adamszablewski.service;

import com.adamszablewski.rooms.Room;
import com.adamszablewski.rooms.RoomAvailabilityCheck;
import com.adamszablewski.rooms.RoomRepository;
import com.adamszablewski.rooms.RoomService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    RoomRepository roomRepository;
    RoomAvailabilityCheck roomAvailabilityCheck;
    RoomService roomService;

    @BeforeEach
    void setUp(){
        roomRepository.deleteAll();
        roomService = new RoomService(roomAvailabilityCheck, roomRepository);
    }


    @Test
    public void RoomService_addRoom_ResponseOK(){

        Room room = Room.builder()
                .roomCategory("Basic")
                .maxNumberOfGuests(2)
                .pricePerNight(250)
                .build();

        when(roomRepository.save(Mockito.any(Room.class))).thenReturn(room);

        Room savedRoom = roomService.createRoom(room);

        Assertions.assertThat(savedRoom).isNotNull();
    }

    @Test
    public void RoomService_getAllRooms_ResponseList(){
       roomService.findAllRooms();

       verify(roomRepository).findAll();
    }

    @Test
    public void canAddRoom(){
        Room room = Room.builder()
                .roomCategory("Other")
                .maxNumberOfGuests(2)
                .pricePerNight(250)
                .build();

        roomService.createRoom(room);

        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);

        verify(roomRepository).save(roomArgumentCaptor.capture());

        Room capturedRoom = roomArgumentCaptor.getValue();

        assertThat(capturedRoom).isEqualTo(room);
    }


    @Test
    public void deleteRoomByIdTest_RemoveSuccessfull() {
        int roomId = 1;
        when(roomRepository.existsById(roomId)).thenReturn(true);
        ResponseEntity<String> response = roomService.deleteRoomById(roomId);

        verify(roomRepository).existsById(roomId);
        verify(roomRepository).deleteById(roomId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Room removed");
    }

    @Test
    public void deleteRoomByIdTest_RemoveNotSuccessfull() {
        int roomId = 1;
        when(roomRepository.existsById(roomId)).thenReturn(false);
        ResponseEntity<String> response = roomService.deleteRoomById(roomId);

        verify(roomRepository).existsById(roomId);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void shouldUpdateRoomById(){
        int id = 1 ;
        Room room = Room.builder()
                .roomCategory("Other")
                .maxNumberOfGuests(4)
                .pricePerNight(250)
                .build();

        Room existingRoom = Room.builder()
                .roomCategory("Other")
                .maxNumberOfGuests(2)
                .pricePerNight(200)
                .build();


        when(roomRepository.findById(id)).thenReturn(Optional.of(existingRoom));

        existingRoom.setRoomCategory(room.getRoomCategory());
        existingRoom.setMaxNumberOfGuests(room.getMaxNumberOfGuests());
        existingRoom.setPricePerNight(room.getPricePerNight());

        when(roomRepository.save(existingRoom)).thenReturn(existingRoom);

        ResponseEntity<Room> response = roomService.updateRoomById(id, room);

        verify(roomRepository, Mockito.times(1)).findById(id);
        verify(roomRepository, Mockito.times(1)).save(existingRoom);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(room);
    }

    @Test
    public void shouldNotUpdateRoomById(){
        int id = 1 ;
        Room room = Room.builder()
                .roomCategory("Other")
                .maxNumberOfGuests(4)
                .pricePerNight(250)
                .build();

        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Room> response = roomService.updateRoomById(id, room);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }



}
