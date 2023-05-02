package com.adamszablewski.security;


import com.adamszablewski.dto.AuthResponseDto;
import com.adamszablewski.dto.LoginDto;
import com.adamszablewski.dto.RegisterDto;
import com.adamszablewski.role.Role;
import com.adamszablewski.role.RoleRepository;
import com.adamszablewski.users.UserInfo;
import com.adamszablewski.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@Service
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private TokenGenerator tokenGenerator;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto){
        if (userRepository.existsByUsername(registerDto.getEmail())){
            return new ResponseEntity<>("username is taken", HttpStatus.BAD_REQUEST);
        }
        UserInfo user = new UserInfo();
        user.setUsername(registerDto.getEmail());
        user.setName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        Role role = roleRepository.findByName("CUSTOMER").get();
        System.out.println(role);
        user.setRoles(Collections.singletonList(role));
        String password = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(password);
        System.out.println(password);
        user.setDiscountLevel("");


        userRepository.save(user);
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto user){
        System.out.println(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

}
