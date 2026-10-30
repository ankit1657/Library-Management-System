package com.LearningSpring.LearningAPI.Service;


import com.LearningSpring.LearningAPI.Exception.ResourceNotFoundException;
import com.LearningSpring.LearningAPI.dto.UserReqDto;
import com.LearningSpring.LearningAPI.dto.UserResDto;
import com.LearningSpring.LearningAPI.entity.UserEntity;
import com.LearningSpring.LearningAPI.Repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserEntity user;
    private UserReqDto reqDto;

    @BeforeEach
    void setUp(){

        user = new UserEntity(1L,"ankit","encodePass","ankit@gmail.com",22,"ROLE_USER",null);

            reqDto = new UserReqDto();
            reqDto.setUserName("ankit");
            reqDto.setPassword("password123");
            reqDto.setEmail("ankit@gmail.com");
            reqDto.setAge(22);
            reqDto.setRole("ROLE_USER");

    }

    //user Succesfull register ho rha ya nhi
    @Test
    void testRegisterUser(){
         when(passwordEncoder.encode(any(String.class))).thenReturn("encodePass");
         when(userRepo.save(any(UserEntity.class))).thenReturn(user);

        UserResDto res = userService.registerUser(reqDto);

        assertNotNull(res);
        assertEquals("ankit",res.getUserName());
        assertEquals("ankit@gmail.com",res.getEmail());

        verify(userRepo,times(1)).save(any(UserEntity.class));
    }

    @Test
     void testGetUserById(){

        when(userRepo.findByUserName("ankit")).thenReturn(Optional.of(user));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        UserResDto res = userService.getUserById(1L,"ankit");
         assertNotNull(res);
         assertEquals("ankit",res.getUserName());

    }
    @Test
    void testGetUserById_NotFound(){

        UserEntity adminUser = new UserEntity(2L, "admin", "pass", "admin@gmail.com", 25, "ROLE_ADMIN", null);


        when(userRepo.findByUserName("admin")).thenReturn(Optional.of(adminUser));

        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(99L, "admin");
        });

        assertEquals("User not found with id99", exception.getMessage());
    }



}
