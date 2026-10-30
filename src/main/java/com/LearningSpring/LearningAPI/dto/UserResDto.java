package com.LearningSpring.LearningAPI.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResDto {

    private Long id;
    private String userName;
//    private String password;
    private String email;
    private int age;
    private String role;
}
