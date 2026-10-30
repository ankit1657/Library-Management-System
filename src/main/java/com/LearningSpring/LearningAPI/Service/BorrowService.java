package com.LearningSpring.LearningAPI.Service;

import com.LearningSpring.LearningAPI.dto.BorrowRecordResDto;
import com.LearningSpring.LearningAPI.entity.BookEntity;
import com.LearningSpring.LearningAPI.entity.BorrowEntity;
import com.LearningSpring.LearningAPI.entity.UserEntity;
import com.LearningSpring.LearningAPI.Exception.ResourceNotFoundException;
import com.LearningSpring.LearningAPI.Repository.BookRepo;
import com.LearningSpring.LearningAPI.Repository.BorrowRecordRepo;
import com.LearningSpring.LearningAPI.Repository.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BorrowService {
    private final BorrowRecordRepo borrowRecordsRepo;
    private final BookRepo bookRepo;
    private final UserRepo userRepo;



    public BorrowRecordResDto borrowBookById(Long bookId, String userName) {
        BookEntity book = bookRepo.findById(bookId).orElseThrow(()->
                                  new ResourceNotFoundException("Book not found with id"+ bookId));
        if(book.getAvailableCopies() <=0 ){
            throw new ResourceNotFoundException("Book is out of stock!");
        }

        UserEntity user = userRepo.findByUserName(userName).orElseThrow(()->
                          new ResourceNotFoundException("User not found "));

        // book update borrow krne par ek km krna
          book.setAvailableCopies(book.getAvailableCopies()-1);
          bookRepo.save(book);


        // Borrow record banana
        BorrowEntity record = new BorrowEntity();
        record.setBook(book);
        record.setUser(user);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setStatus("BORROWED");

        BorrowEntity savedRecord = borrowRecordsRepo.save(record);
        
        return mapToDto(savedRecord);

    }



    public BorrowRecordResDto returnBookById(Long borrowID) {

        BorrowEntity record = borrowRecordsRepo.findById(borrowID)
                .orElseThrow(()->new ResourceNotFoundException("Borrow record not found"));

        if("RETURNED".equals(record.getStatus())){
            throw new RuntimeException("Book is alredy returned");
        }

        // book ka stack waps badhana
        BookEntity book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies()+1);
        bookRepo.save(book);

        // Record update karna
        record.setReturnDate(LocalDate.now());
        record.setStatus("RETURNED");

        BorrowEntity savedRecord = borrowRecordsRepo.save(record);
        return mapToDto(savedRecord);
    }

    public List<BorrowRecordResDto> getMyHistory(String userName) {

        UserEntity user = userRepo.findByUserName(userName).orElseThrow(()->
                  new ResourceNotFoundException("User not found"));

           return borrowRecordsRepo.findByUserId(user.getId())
                   .stream()
                   .map(this::mapToDto)
                   .toList();

    }

    // admin ke liye sare records dekhna
    public List<BorrowRecordResDto> getAllRecords() {

        return borrowRecordsRepo.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    private BorrowRecordResDto mapToDto(BorrowEntity record) {
        return new BorrowRecordResDto(
                record.getId(),
                record.getBook().getTitle(),
                record.getUser().getUserName(),
                record.getBorrowDate(),
                record.getDueDate(),
                record.getReturnDate(),
                record.getStatus()
        );
    }
}
