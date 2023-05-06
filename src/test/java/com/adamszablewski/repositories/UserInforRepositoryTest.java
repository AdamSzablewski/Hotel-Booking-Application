package com.adamszablewski.repositories;

import com.adamszablewski.users.UserInfo;
import com.adamszablewski.users.UserRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
public class UserInforRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldCheckIfUserExistsByUsername_ReturnTrue(){

        UserInfo user = UserInfo.builder()
                .id(1)
                .name("user")
                .username("user@test.com")
                .lastName("1")
                .phoneNumber("12345678")
                .build();
        userRepository.save(user);

        boolean exists = userRepository.existsByUsername(user.getUsername());

        assertThat(exists).isTrue();
    }
    @Test
    public void shouldCheckIfUserExistsByUsername_ReturnFalse(){

        boolean exists = userRepository.existsByUsername("nothing");

        assertThat(exists).isFalse();
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturnUserObjectByUsername(){

        UserInfo user = UserInfo.builder()
                .id(1)
                .name("user")
                .username("user@test.com")
                .lastName("1")
                .phoneNumber("12345678")
                .build();

        userRepository.save(user);

        UserInfo userFromDb = userRepository.findByUsername(user.getUsername()).get();
        assertThat(userFromDb.getUsername()).isEqualTo("user@test.com");
    }

    @Test
    public void shouldReturnUserObjectByPhoneNumber(){
        UserInfo user = UserInfo.builder()
                .id(1)
                .name("user")
                .username("user@test.com")
                .lastName("1")
                .phoneNumber("12345678")
                .build();

        userRepository.save(user);

        UserInfo userFromDb = userRepository.findByUsername(user.getUsername()).get();
        assertThat(userFromDb.getPhoneNumber()).isEqualTo("12345678");
    }
}
