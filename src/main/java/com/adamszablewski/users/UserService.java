package com.adamszablewski.users;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<UserInfo> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<UserInfo> findUserById(int id){
        return userRepository.findById(id);
    }

    public Optional<UserInfo> findUserByUsername(String email){
        return userRepository.findByUsername(email);
    }

    public ResponseEntity<UserInfo> updateUserById(int user_id, UserInfo user) {
        Optional<UserInfo> optionalUser = userRepository.findById(user_id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserInfo existingUser = optionalUser.get();
        existingUser.setUsername(user.getUsername());
        existingUser.setName(user.getName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        UserInfo updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);

    }

    public ResponseEntity<UserInfo> updatePasswordByEmail(String email, UserInfo userInfo) {
        Optional<UserInfo> optionalUser = userRepository.findByUsername(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserInfo existingUser = optionalUser.get();
        existingUser.setPassword(userInfo.getPassword());
        return ResponseEntity.ok(existingUser);

    }

    public ResponseEntity<UserInfo> updateEmailByEmail(String email, UserInfo userInfo) {
        Optional<UserInfo> optionalUser = userRepository.findByUsername(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserInfo existingUser = optionalUser.get();
        existingUser.setUsername(userInfo.getUsername());
        return ResponseEntity.ok(existingUser);
    }

    public ResponseEntity<UserInfo> updatePhoneNumberByEmail(String email, UserInfo userInfo) {
        Optional<UserInfo> optionalUser = userRepository.findByUsername(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserInfo existingUser = optionalUser.get();
        existingUser.setPhoneNumber(userInfo.getPhoneNumber());
        return ResponseEntity.ok(existingUser);
    }

    public  ResponseEntity<String> deleteUserByEmail(String email){
        Optional<UserInfo> optionalUser = userRepository.findByUsername(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserInfo existingUser = optionalUser.get();
        int userId = existingUser.getId();
        userRepository.deleteById(userId);
        return ResponseEntity.ok("User deleted");
    }

    public ResponseEntity<UserInfo> changeAuthoritiesForUserByEmail(String email, UserInfo userInfo) {
        Optional<UserInfo> optionalUser = userRepository.findByUsername(email);
        UserInfo user = optionalUser.get();
        user.setRoles(userInfo.getRoles());
        return ResponseEntity.ok(user);
    }
}
