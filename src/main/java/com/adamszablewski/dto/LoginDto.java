package com.adamszablewski.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
    private String phoneNumber;
}
