package com.adamszablewski.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
}
