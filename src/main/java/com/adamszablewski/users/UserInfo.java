package com.adamszablewski.users;

import com.adamszablewski.reservations.Reservation;
import com.adamszablewski.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "rid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "rid"))
    private List<Role> roles;

    @Column(name = "last_name")
    private String lastName;

    private String discountLevel;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "points")
    private double points;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;


    public void addPoints(double points) {
        this.points = points;
    }
}
