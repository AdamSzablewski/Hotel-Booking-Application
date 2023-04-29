package com.adamszablewski.users;

import com.adamszablewski.reservations.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "user_id"))
//    private List<Role> roles = new ArrayList<>();

    @Column(name = "last_name")
    private String lastName;

    private String discountLevel;

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
