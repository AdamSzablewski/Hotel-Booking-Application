package com.adamszablewski.security;


import com.adamszablewski.role.Role;
import com.adamszablewski.users.UserInfo;
import com.adamszablewski.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new User(
                user.getUsername()
                ,user.getPassword()
                ,mapRolesTOAuthorities(user.getRoles())
        );
    }


    private Collection<GrantedAuthority> mapRolesTOAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
