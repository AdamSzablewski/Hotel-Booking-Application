package com.adamszablewski.reservations;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ReservationController {

    ReservationService reservationService;

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations(){
        return reservationService.findAllReservations();
    }


    @GetMapping("/reservations/id/{res_id}")
    public Optional<Reservation> getReservationById(@PathVariable Long res_id){
        return reservationService.findReservationsById(res_id);
    }

    @GetMapping("/reservations/email/{email}")
    public List<Reservation> getReservationsByEmail(@PathVariable String email){
        return reservationService.findAllReservationsForUserByEmail(email);
    }
    @GetMapping("/reservations/email/{email}")
    public List<Reservation> getReservationsByPhoneNuber(@PathVariable String phoneNumber){
        return reservationService.findAllReservationsForUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("/reservations/rooms/{room_id}")
    public List<Reservation> getReservationsForRoom(@PathVariable int room_id){
        return reservationService.findAllReservationsForRoom(room_id);
    }

    @PostMapping("/reservations")
    public ResponseEntity<String> createReservation(@RequestBody @Valid Reservation reservation){
        return reservationService.createReservation(reservation);
    }

    @PutMapping("/reservations/{res_id}")
    public ResponseEntity<Reservation> updateReservationById(@PathVariable Long res_id,
                                                             @RequestBody @Valid Reservation reservation){
        return reservationService.updateReservationById(res_id, reservation);
    }

    @DeleteMapping("/reservations/{res_id}")
    public ResponseEntity<String> deleteReservationById(@PathVariable Long res_id){
        return reservationService.deleteReservationById(res_id);
    }

    @DeleteMapping("/reservations/email/{email}")
    public ResponseEntity<String> deleteAllReservationsForUser(@PathVariable String email){
        return reservationService.deleteAllForUserByEmail(email);
    }

}
