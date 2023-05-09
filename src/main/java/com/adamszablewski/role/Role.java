package com.adamszablewski.role;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;
    private String name;

}
