package com.LearningSpring.LearningAPI.Controller;


import com.LearningSpring.LearningAPI.dto.BookReqDto;
import com.LearningSpring.LearningAPI.dto.BookResDto;
import com.LearningSpring.LearningAPI.Service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookContoller {

    private final BookService bookService;

   // Sirf ADMIN nayi kitab add kar sakta hai
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<BookResDto> addBook(@Valid @RequestBody BookReqDto bookReqDto){

        BookResDto saveBook = bookService.addBook(bookReqDto);
        return new ResponseEntity<>(saveBook,HttpStatus.CREATED);
    }

    // book ko admin aur user dono dekh skta hai
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/readAll")
    public ResponseEntity<List<BookResDto>> getAllBook(){
        List<BookResDto> books = bookService.getAllBooks();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    // user aur admin dono id se book dekh stke hai
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookResDto> getBookById(@PathVariable Long id){
        BookResDto book = bookService.getBookById(id);
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    // bss admin hi update kr skta hai book ko
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<BookResDto> updateBookById(@Valid @RequestBody BookReqDto bookReqDto ,
                                                     @PathVariable Long id){
        BookResDto book = bookService.updateBookById(bookReqDto,id);
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
        return new ResponseEntity<>("Book deleted successfully ",HttpStatus.NO_CONTENT);
    }



}
