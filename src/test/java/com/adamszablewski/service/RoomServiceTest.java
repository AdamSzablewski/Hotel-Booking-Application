package com.adamszablewski.service;

import com.adamszablewski.rooms.Room;
import com.adamszablewski.rooms.RoomRepository;
import com.adamszablewski.rooms.RoomService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    RoomService roomService;

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

    }
}
