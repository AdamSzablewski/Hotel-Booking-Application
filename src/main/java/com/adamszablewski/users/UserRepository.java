package com.adamszablewski.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByUsername(String username);

    Optional<UserInfo> findByPhoneNumber(String phoneNumber);
    Boolean existsByUsername(String email);
}
