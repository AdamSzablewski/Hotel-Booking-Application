package com.adamszablewski.rooms;

import com.adamszablewski.users.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class RoomController {

    RoomService roomService;

    @GetMapping("/rooms")
    public List<Room> getRooms(){
        return roomService.findAllRooms();
    }

    @GetMapping("/rooms/{room_id}")
    public Optional<Room> getRoomById(@PathVariable int room_id){
        return roomService.findRoomById(room_id);
    }

    @PostMapping("/rooms")
    public Room createRoom(@RequestBody Room room) {

        return roomService.createRoom(room);
    }

    @PutMapping("/rooms/{room_id}")
    public ResponseEntity<Room> updateRoom(@PathVariable int room_id, @RequestBody Room room){
        return roomService.updateRoomById(room_id, room);
    }

    @DeleteMapping("/rooms/{room_id}")
    public  ResponseEntity<String> deleteRoomById(@PathVariable int room_id){
        return roomService.deleteRoomById(room_id);
    }




}
