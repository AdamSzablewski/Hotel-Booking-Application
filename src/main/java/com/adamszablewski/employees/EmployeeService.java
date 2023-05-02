package com.adamszablewski.employees;

import com.adamszablewski.role.Role;
import com.adamszablewski.role.RoleRepository;
import com.adamszablewski.users.UserInfo;
import com.adamszablewski.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> registerNewEmployee(UserInfo userInfo) {
        if (userRepository.existsByUsername(userInfo.getUsername())){
            return new ResponseEntity<>("username is taken", HttpStatus.BAD_REQUEST);
        }
        UserInfo user = new UserInfo();
        user.setUsername(userInfo.getUsername());
        user.setName(userInfo.getName());
        user.setLastName(userInfo.getLastName());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        Role role = roleRepository.findByName("EMPLOYEE").get();
        user.setRoles(List.of(role));
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setDiscountLevel("");

        userRepository.save(user);
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }

    public ResponseEntity<String> registerNewManager(UserInfo userInfo) {
        if (userRepository.existsByUsername(userInfo.getUsername())){
            return new ResponseEntity<>("username is taken", HttpStatus.BAD_REQUEST);
        }
        UserInfo user = new UserInfo();
        user.setUsername(userInfo.getUsername());
        user.setName(userInfo.getName());
        user.setLastName(userInfo.getLastName());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        Role role = roleRepository.findByName("MANAGER").get();
        user.setRoles(List.of(role));
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setDiscountLevel("");

        userRepository.save(user);
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }
    }

