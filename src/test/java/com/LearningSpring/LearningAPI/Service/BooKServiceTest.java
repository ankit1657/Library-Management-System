package com.LearningSpring.LearningAPI.Service;


import com.LearningSpring.LearningAPI.Exception.ResourceNotFoundException;
import com.LearningSpring.LearningAPI.Repository.BookRepo;
import com.LearningSpring.LearningAPI.dto.BookReqDto;
import com.LearningSpring.LearningAPI.dto.BookResDto;
import com.LearningSpring.LearningAPI.entity.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BooKServiceTest {

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookService bookService;

    private BookEntity book;
    private BookReqDto reqDto;

    @BeforeEach
    void setUp(){

        book = new BookEntity(1L, "Spring Boot", "Craig Walls", "ISBN123", 5);

        reqDto = new BookReqDto();
        reqDto.setTitle("Spring Boot");
        reqDto.setAuthor("Craig Walls");
        reqDto.setIsbn("ISBN123");
        reqDto.setAvailableCopies(5);
    }

    @Test
    void testAddBook(){

        when(bookRepo.save(any(BookEntity.class))).thenReturn(book);

        BookResDto result = bookService.addBook(reqDto);

        assertNotNull(result);
        assertEquals("Spring Boot", result.getTitle());
        verify(bookRepo, times(1)).save(any(BookEntity.class));

    }

    // id se kitab mil rha ki nhi
    @Test
    void testGetBookById_success(){
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));

        BookResDto res = bookService.getBookById(1L);

        assertNotNull(res);

        assertEquals(1L,res.getId());
        assertEquals("Spring Boot",res.getTitle());

    }

    // agar kitab na mile to exception throw ho rha ki nhi

    @Test
    void testGetBookById_NotFound(){
        when(bookRepo.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class,()->{
            bookService.getBookById(99L);
        });

        assertEquals("Book not found with id99" , exception.getMessage());
    }


}
