package com.adamszablewski.role;

import jakarta.persistence.*;
import lombok.Data;

//@Entity
@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;
    private String name;

}
