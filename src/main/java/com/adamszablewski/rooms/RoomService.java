package com.adamszablewski.rooms;

import com.adamszablewski.users.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class RoomService {

    RoomRepository roomRepository;
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> findRoomById(int room_id) {
        return roomRepository.findById(room_id);
    }

    public ResponseEntity<Room> createRoom(Room room) {
        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }
    public ResponseEntity<Room> updateRoomById(int room_id, Room room) {
        Optional<Room> optionalRoom = roomRepository.findById(room_id);
        if (optionalRoom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Room existingRoom = optionalRoom.get();
        existingRoom.setRoomCategory(room.getRoomCategory());
        existingRoom.setMaxNumberOfGuests(room.getMaxNumberOfGuests());
        existingRoom.setPricePerNight(room.getPricePerNight());

        Room updatedRoom = roomRepository.save(existingRoom);
        return ResponseEntity.ok(updatedRoom);
    }

    public ResponseEntity<String> deleteRoomById(int room_id) {
        roomRepository.deleteById(room_id);
        return ResponseEntity.ok("Room removed");
    }
}
