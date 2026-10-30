package com.LearningSpring.LearningAPI.Controller;


import com.LearningSpring.LearningAPI.dto.UserReqDto;
import com.LearningSpring.LearningAPI.dto.UserResDto;
import com.LearningSpring.LearningAPI.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")

public class UserController {

    private final UserService userService;

    // user registration public signup/login ke liye
    @PostMapping("/register")
    public ResponseEntity<UserResDto> registerUser(@Valid @RequestBody UserReqDto userReqDto){
         UserResDto registeredUser = userService.registerUser(userReqDto);
         return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    // user bss apni id dekh sktahi aur admin dusre ka id daalkr v dekh sktahai
    @PreAuthorize("hasAnyRole('USER',ADMIN)")
    @GetMapping("/{id}")
    public ResponseEntity<UserResDto> getUserById(@PathVariable Long id, Principal principal){
          String currUserName = principal.getName();
         UserResDto userId = userService.getUserById(id,currUserName);
         return new ResponseEntity<>(userId,HttpStatus.OK);
    }

    // admin sara user ka list dekh skta hai
     @PreAuthorize("hasRole('ADMIN')")
     @GetMapping("/all")
    public ResponseEntity<List<UserResDto>> getAllUser(){
         List<UserResDto> users = userService.getAllUsers(99L);
         return new ResponseEntity<>(users,HttpStatus.OK);
     }


}
