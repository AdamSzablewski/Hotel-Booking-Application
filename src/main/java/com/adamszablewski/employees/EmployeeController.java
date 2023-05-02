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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
public class EmployeeController {


    EmployeeService employeeService;

    @PostMapping("/register/employee")
    public ResponseEntity<String> registerEmployee(@RequestBody UserInfo userInfo) {
        return employeeService.registerNewEmployee(userInfo);
    }

    @PostMapping("/register/manager")
    public ResponseEntity<String> registerManager(@RequestBody UserInfo userInfo) {
        return employeeService.registerNewManager(userInfo);
    }
}
