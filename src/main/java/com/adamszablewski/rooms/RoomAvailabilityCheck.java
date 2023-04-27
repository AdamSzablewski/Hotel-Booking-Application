package com.adamszablewski.rooms;

import com.adamszablewski.reservations.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RoomAvailabilityCheck {


    public boolean isAvailableDuringPeriod(Room room, Reservation reservation) {
        List<Reservation> roomReservations = room.getReservations();
        for (Reservation r : roomReservations) {
            if (r.getDeparture().isAfter(reservation.getArrival()) && reservation.getDeparture().isAfter(r.getArrival())) {
                return false;
            }
        }
        return true;
    }

}
