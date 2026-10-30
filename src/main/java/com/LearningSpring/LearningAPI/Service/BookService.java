package com.LearningSpring.LearningAPI.Service;

import com.LearningSpring.LearningAPI.dto.BookReqDto;
import com.LearningSpring.LearningAPI.dto.BookResDto;
import com.LearningSpring.LearningAPI.entity.BookEntity;
import com.LearningSpring.LearningAPI.Exception.ResourceNotFoundException;
import com.LearningSpring.LearningAPI.Repository.BookRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class BookService {

    private  final BookRepo bookRepo;

    @CacheEvict(value = "books", allEntries = true)
    public BookResDto addBook(@Valid BookReqDto reqDto) {

        BookEntity book = new BookEntity();
        book.setTitle(reqDto.getTitle());
        book.setAuthor(reqDto.getAuthor());
        book.setIsbn(reqDto.getIsbn());
        book.setAvailableCopies(reqDto.getAvailableCopies());

        BookEntity savedBook = bookRepo.save(book);
        return mapToDto(savedBook);
    }


    private BookResDto mapToDto(BookEntity book) {
        return new BookResDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getAvailableCopies()
        );
    }

    @Cacheable(value = "books")
    public List<BookResDto> getAllBooks() {

        List<BookEntity> bookList = bookRepo.findAll();
        return bookList.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Cacheable(value = "books", key = "#id")
    public BookResDto getBookById(Long id) {
        BookEntity book = bookRepo.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Book not found with id"+ id));
         return mapToDto(book);
    }


    @CacheEvict(value = "books", allEntries = true)
    public BookResDto updateBookById(@Valid BookReqDto reqDto, Long id) {
        BookEntity book = bookRepo.findById(id).orElseThrow(()->
                                 new ResourceNotFoundException("Book not found with id " + id));
         book.setTitle(reqDto.getTitle());
         book.setAuthor(reqDto.getAuthor());
         book.setIsbn(reqDto.getIsbn());;
         book.setAvailableCopies(reqDto.getAvailableCopies());

         BookEntity updateBook = bookRepo.save(book);
         return mapToDto(updateBook);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void deleteBookById(Long id) {
        if(!bookRepo.existsById(id)){
            throw new ResourceNotFoundException("book not found  with id "+ id);
        }
        bookRepo.deleteById(id);
    }
}
