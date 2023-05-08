package com.adamszablewski.rooms;

import com.adamszablewski.reservations.Reservation;
import com.adamszablewski.users.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class RoomService {

    RoomAvailabilityCheck roomAvailabilityCheck;

    RoomRepository roomRepository;
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> findRoomById(int room_id) {
        return roomRepository.findById(room_id);
    }

    public Room createRoom(Room room) {
        roomRepository.save(room);
        return room;
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
        if (!roomRepository.existsById(room_id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        roomRepository.deleteById(room_id);
        return ResponseEntity.ok("Room removed");
    }

    public List<Room> findAvailableRoomsForReservation(Reservation reservation) {
        //todo add option to add multiple rooms in one go
        int numOfGuests = reservation.getGuests();
        int currentCapacity = 0;
        List<Room> availableRooms = new ArrayList<>();
        List<Room> allRooms = findAllRooms();

        for (Room room : allRooms) {
            if (room.getMaxNumberOfGuests() >= numOfGuests &&
                    room.getRoomCategory().equals(reservation.getRoomClass()) &&
                    roomAvailabilityCheck.isAvailableDuringPeriod(room, reservation)) {
                availableRooms.add(room);
                return availableRooms;
            }
            else if (roomAvailabilityCheck.isAvailableDuringPeriod(room, reservation)){
                availableRooms.add(room);
                currentCapacity += room.getMaxNumberOfGuests();
            }
            if (numOfGuests <= currentCapacity ){
                return availableRooms;
            }
        }
        return availableRooms;
    }

}
