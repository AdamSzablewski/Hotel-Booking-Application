package com.adamszablewski.service;

import com.adamszablewski.rooms.RoomService;
import com.adamszablewski.users.UserInfo;
import com.adamszablewski.users.UserRepository;
import com.adamszablewski.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserService userService;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        userService = new UserService(userRepository);
    }


    @Test
    public void updateUserById_shouldReturnUpdatedUser(){
        int id= 1;
        UserInfo user = UserInfo.builder()
                .username("new")
                .build();
        UserInfo dbUser = UserInfo.builder()
                .username("test")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(dbUser));
        when(userRepository.save(dbUser)).thenReturn(dbUser);

        ResponseEntity<UserInfo> response = userService.updateUserById(id, user);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void updateUserById_shouldReturnNotFoundStatus(){
        int id= 1;
        UserInfo user = UserInfo.builder()
                .username("new")
                .build();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<UserInfo> response = userService.updateUserById(id, user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void updatePasswordByEmail_shouldReturnUpdatedUser(){
        String email= "test@test.com";
        UserInfo user = UserInfo.builder()
                .username("test@test.com")
                .password("123")
                .build();
        UserInfo dbUser = UserInfo.builder()
                .username("test@test.com")
                .password("abc")
                .build();

        when(userRepository.findByUsername(email)).thenReturn(Optional.of(dbUser));
        when(userRepository.save(dbUser)).thenReturn(dbUser);

        ResponseEntity<String> response = userService.updatePasswordByEmail(email, user);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getPassword()).isEqualTo(dbUser.getPassword());
    }

    @Test
    public void updatePasswordByEmail_shouldReturnNotFound(){
        String email= "test@test.com";
        UserInfo user = UserInfo.builder()
                .username("test@test.com")
                .password("123")
                .build();

        when(userRepository.findByUsername(email)).thenReturn(Optional.empty());


        ResponseEntity<String> response = userService.updatePasswordByEmail(email, user);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }


}
