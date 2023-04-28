package com.adamszablewski.reservations;

import com.adamszablewski.rooms.Room;
import com.adamszablewski.rooms.RoomAvailabilityCheck;
import com.adamszablewski.rooms.RoomRepository;
import com.adamszablewski.rooms.RoomService;
import com.adamszablewski.users.UserInfo;
import com.adamszablewski.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ReservationService {

    ReservationRepository reservationRepository;
    UserRepository userRepository;
    RoomService roomService;
    RoomPrice roomPrice;

    RoomAvailabilityCheck roomAvailabilityCheck;

    RoomRepository roomRepository;


    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> findAllReservationsForUserByEmail(String email) {
        Optional<UserInfo> optionalUser = userRepository.findByUsername(email);
        if (optionalUser.isEmpty()) {
            return new ArrayList<>();
        }
        UserInfo user = optionalUser.get();
        return user.getReservations();
    }

    public List<Reservation> findAllReservationsForUserByPhoneNumber(String phoneNumber) {
        Optional<UserInfo> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()) {
            return new ArrayList<>();
        }
        UserInfo user = optionalUser.get();
        return user.getReservations();
    }

    public List<Reservation> findAllReservationsForRoom(int roomNumber) {
        Optional<Room> optionalRoom = roomRepository.findById(roomNumber);
        if (optionalRoom.isEmpty()) {
            return new ArrayList<>();
        }
        Room room = optionalRoom.get();
        return room.getReservations();

    }



    public Optional<Reservation> findReservationsById(Long res_id) {
        return reservationRepository.findById(res_id);
    }

    public ResponseEntity<String> createReservation(Reservation reservation) {
        int numOfGuests = reservation.getGuests();
        double roomPricetemp = 0;
        boolean roomsAvailable = false;
//        List<Room> availableRoomsForReservation = new ArrayList<>();
//        List<Room> allRooms = roomService.findAllRooms();
//            for (Room room : allRooms){
//                if (room.getMaxNumberOfGuests() >= numOfGuests &&
//                        room.getRoomCategory().equals(reservation.getRoomClass())
//                            && roomAvailabilityCheck.isAvailableDuringPeriod(room, reservation)){
//                    if(!roomsAvailable){
//                        roomPricetemp = room.getPricePerNight();
//                        availableRoomsForReservation.add(room);
//                    }
//                    roomsAvailable = true;
//                }
//            }
        List<Room> selectedRooms = roomService.findAvailableRoomsForReservation(reservation);

        if (!selectedRooms.isEmpty()) {
            reservation.setRooms(selectedRooms);
            reservation.setTotalPrice(0);
            reservationRepository.save(reservation);
            reservation.setTotalPrice(roomPrice.calculateTotalRoomPrice(reservation));
            Optional<UserInfo> optionalUser = userRepository.findByUsername(reservation.getUsername());
            UserInfo user = optionalUser.get();
            reservation.setUser(user);

            user.addPoints(roomPrice.calculatePointsForUser(roomPricetemp * reservation.getNumberOfNights(reservation)));
            roomPrice.calculatePointsForUser(roomPricetemp);
            reservationRepository.save(reservation);
            //todo add price and number of night information as return
            return ResponseEntity.ok("Reservation created");
        }
    return ResponseEntity.ok("No available rooms in this category");
    }

    public ResponseEntity<Reservation> updateReservationById(Long res_id, Reservation reservation) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(res_id);
        if (optionalReservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Reservation existingReservation = optionalReservation.get();
        existingReservation.setArrival(reservation.getArrival());
        existingReservation.setDeparture(reservation.getDeparture());

        existingReservation.setTotalPrice(roomPrice.calculateTotalRoomPrice(reservation));
        Reservation updatedReservation = reservationRepository.save(existingReservation);
        return ResponseEntity.ok(updatedReservation);
    }

    public ResponseEntity<String> deleteReservationById(Long res_id) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(res_id);
        if (optionalReservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Reservation existingReservation = optionalReservation.get();


        List<Room> rooms = existingReservation.getRooms();
        for(Room room : rooms){
            roomRepository.save(room);
        }
        reservationRepository.deleteById(res_id);
        return ResponseEntity.ok("Reservation cancelled");
    }

    public ResponseEntity<String> deleteAllForUserByEmail(String email){
//todo fix this method not deleting reservations
        List<Reservation> reservations = findAllReservationsForUserByEmail(email);

        if (reservations.isEmpty()) {
            return ResponseEntity.ok("No reservations found for user");
        }

        for (Reservation reservation : reservations){
            deleteReservationById(reservation.getId());
        }
        return ResponseEntity.ok("All reservations for user have been cancelled ");
    }


    public List<Reservation> findAllReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findByArrivalBetween(startDate, endDate);
    }
}
