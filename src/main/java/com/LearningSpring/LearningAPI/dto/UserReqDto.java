package com.LearningSpring.LearningAPI.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserReqDto {


    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 20,message = "Username must be between 2 and  20 characters ")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 6,message = "Password  must be at least 6 characters long ")
    private String password;

    @Email(message = "Email is Required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 18 , message ="Minimum age is  18")
    private Integer age;

    private String role;// "ROLE_USER" ya "ROLE_ADMIN"

}
