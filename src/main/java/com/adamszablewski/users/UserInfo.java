package com.adamszablewski.users;

import com.adamszablewski.reservations.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "userInfo")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserInfo {

    @Id
    private int id;

    @Column(name = "first_name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "points")
    private double points;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public void addPoints(double points) {
        this.points = points;
    }
}
