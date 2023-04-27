package com.adamszablewski.users;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    UserService userService;

    @GetMapping("/users")
    public List<UserInfo> findAllusers(){
        return userService.findAllUsers();
    }

    @GetMapping("/users/id/{user_id}")
    public Optional<UserInfo> userById(@PathVariable int user_id){
        return userService.findUserById(user_id);
    }

    @GetMapping("/users/email/{email}")
    public Optional<UserInfo> userByEmail(@PathVariable String email){
        return userService.findUserByUsername(email);
    }

    @PutMapping("users/id/{user_id}")
        public ResponseEntity<UserInfo> updateUser(@PathVariable int user_id, @RequestBody UserInfo user){
            return userService.updateUserById(user_id, user);

    }

    @PatchMapping("/users/id/password/{email}")
        public ResponseEntity<UserInfo> changePassword(@PathVariable String email, @RequestBody UserInfo userInfo) {
            return userService.updatePasswordByEmail(email, userInfo);
    }

    @PatchMapping("/users/id/email/{email}")
    public ResponseEntity<UserInfo> changeEmail(@PathVariable String email, @RequestBody UserInfo userInfo) {
        return userService.updateEmailByEmail(email, userInfo);
    }

    @PatchMapping("/users/id/phoneNumber/{email}")
    public ResponseEntity<UserInfo> changeNumber(@PathVariable String email, @RequestBody UserInfo userInfo) {
        return userService.updatePhoneNumberByEmail(email, userInfo);
    }

    @DeleteMapping("/users/email/{email}")
    public  ResponseEntity<String> deleteUser(@PathVariable String email){
        return userService.deleteUserByEmail(email);
    }






}
