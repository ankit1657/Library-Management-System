package com.LearningSpring.LearningAPI.Service;


import com.LearningSpring.LearningAPI.entity.UserEntity;
import com.LearningSpring.LearningAPI.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepo.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found" + username));


        // Database se role nikal kar GrantedAuthority mein convert karna
        String role = user.getRole() != null ? user.getRole() : "ROLE_USER";
        List<GrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );


    }
}
