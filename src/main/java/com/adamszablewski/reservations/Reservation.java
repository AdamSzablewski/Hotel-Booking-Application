package com.adamszablewski.reservations;

import com.adamszablewski.rooms.Room;
import com.adamszablewski.users.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    //todo add return messege when error

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserInfo user;

    @Min(value = 1, message = "The number of guests must be at least 1")
    private int guests;

    @NotEmpty(message = "The room class must not be empty")
    private String roomClass;

    @NotEmpty(message = "The room class must not be empty")
    private String username;
    private double totalPrice;

    @NotNull
    @Future(message = "The arrival date must be in the future")
    private LocalDate arrival;

    @NotNull
    @Future(message = "The arrival date must be in the future")
    private LocalDate departure;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "reservation_room",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    @JsonIgnore
    private List<Room> rooms;

    public Long getNumberOfNights(Reservation reservation){
        return ChronoUnit.DAYS.between(reservation.getArrival(), reservation.getDeparture());
    }
}