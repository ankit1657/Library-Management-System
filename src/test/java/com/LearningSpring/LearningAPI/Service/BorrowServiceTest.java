package com.LearningSpring.LearningAPI.Service;

import com.LearningSpring.LearningAPI.Exception.ResourceNotFoundException;
import com.LearningSpring.LearningAPI.Repository.BookRepo;
import com.LearningSpring.LearningAPI.Repository.BorrowRecordRepo;
import com.LearningSpring.LearningAPI.Repository.UserRepo;
import com.LearningSpring.LearningAPI.dto.BorrowRecordResDto;
import com.LearningSpring.LearningAPI.entity.BookEntity;
import com.LearningSpring.LearningAPI.entity.BorrowEntity;
import com.LearningSpring.LearningAPI.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*; // <-- Yeh sahi import hai JUnit 5 ka
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowServiceTest {

    @Mock
    private BorrowRecordRepo borrowRecordsRepo;
    @Mock
    private BookRepo bookRepo;
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private BorrowService borrowService;

    private BorrowEntity borrow;
    private UserEntity user;
    private BookEntity book;

    @BeforeEach
    void setUp(){
        book = new BookEntity(1L, "Spring Boot", "Craig Walls", "ISBN123", 5);
        user = new UserEntity(1L, "ankit", "encodedPass", "ankit@gmail.com", 22, "ROLE_USER", null);

        // Dummy Borrow Record
        borrow = new BorrowEntity();
        borrow.setId(1L);
        borrow.setBook(book);
        borrow.setUser(user);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setDueDate(LocalDate.now().plusDays(14));
        borrow.setStatus("BORROWED");
    }

    // Kitab successfully borrow ho rahi hai ya nahi
    @Test
    void testBorrow_success(){
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(userRepo.findByUserName("ankit")).thenReturn(Optional.of(user));
        when(borrowRecordsRepo.save(any(BorrowEntity.class))).thenReturn(borrow);

        BorrowRecordResDto res = borrowService.borrowBookById(1L, "ankit");

        assertNotNull(res);
        assertEquals("Spring Boot", res.getBookTitle());
        assertEquals("ankit", res.getUserName());
        assertEquals("BORROWED", res.getStatus());

        verify(bookRepo, times(1)).save(any(BookEntity.class));
        verify(borrowRecordsRepo, times(1)).save(any(BorrowEntity.class));
    }

    // if book is out of stock so we generate exception

    @Test
    void testBorrowBook_OutOfStock(){
        book.setAvailableCopies(0);

        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));

        Exception exception = assertThrows(ResourceNotFoundException.class,()->{

            borrowService.borrowBookById(1L,"ankit");
        });
        assertEquals("Book is out of stock!",exception.getMessage());
    }


    // Kitab successfully return ho rahi hai ya nahi
    @Test
    void testReturnBook_success(){

        when(borrowRecordsRepo.findById(1L)).thenReturn(Optional.of(borrow));
        when(borrowRecordsRepo.save(any(BorrowEntity.class))).thenReturn(borrow);
        BorrowRecordResDto result = borrowService.returnBookById(1L);

        assertNotNull(result);
        assertEquals("RETURNED", result.getStatus());
        verify(bookRepo, times(1)).save(any(BookEntity.class));


    }
}