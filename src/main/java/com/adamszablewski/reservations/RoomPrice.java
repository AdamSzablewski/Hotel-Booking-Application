package com.adamszablewski.reservations;

import com.adamszablewski.rooms.Room;
import com.adamszablewski.users.UserInfo;
import com.adamszablewski.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RoomPrice {

    UserRepository userRepository;


    private double calculateBasicPrice(Reservation reservation){
        List<Room> rooms = reservation.getRooms();
        double totalPricePerKnightForRooms = 0;

        for(Room room : rooms){
            totalPricePerKnightForRooms += room.getPricePerNight();
        }
        long nights = reservation.getNumberOfNights(reservation);
        return totalPricePerKnightForRooms * nights;
    }


    private double calculateLoyaltyDiscount(Reservation reservation){
        Optional<UserInfo> optionalUser = userRepository.findByUsername(reservation.getUsername());
        UserInfo user = optionalUser.get();
        double currentPoints = user.getPoints();

        if(currentPoints >= 10000){
            if(!user.getDiscountLevel().equals("30%")){
                user.setDiscountLevel("30%");
            }
            return  0.7;
        }
        else if(currentPoints >= 5000){
            if(!user.getDiscountLevel().equals("15%")){
                user.setDiscountLevel("15%");
            }
            return  0.85;
        }
        else if(currentPoints >= 1000){
            if(!user.getDiscountLevel().equals("10%")){
                user.setDiscountLevel("10%");
            }
            return  0.9;
        }
        return 1;
    }

    public double calculateTotalRoomPrice(Reservation reservation){
        return calculateBasicPrice(reservation) * calculateLoyaltyDiscount(reservation);
    }

    public double calculatePointsForUser(double spent){
        return spent * 2;
    }
}
