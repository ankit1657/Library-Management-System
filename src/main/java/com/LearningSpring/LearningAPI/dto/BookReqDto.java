package com.LearningSpring.LearningAPI.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookReqDto {


    @NotBlank(message = "Book title is required")
    private String title;

    @NotBlank(message = "Author name  is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    private String isbn;

   @NotNull(message = "Available copies are required")
   @Min(value = 0,message = "Copies cannot be negative")
    private Integer availableCopies;

}
