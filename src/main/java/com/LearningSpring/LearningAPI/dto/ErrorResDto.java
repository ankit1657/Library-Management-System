package com.LearningSpring.LearningAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResDto {

    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String message;


}
