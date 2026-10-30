package com.LearningSpring.LearningAPI.Controller;

import com.LearningSpring.LearningAPI.dto.BorrowRecordResDto;
import com.LearningSpring.LearningAPI.Service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    private final BorrowService borrowService;

  // user book borrow krega
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/book/{bookId}")
    public ResponseEntity<BorrowRecordResDto> borrowBook(@PathVariable Long bookId, Principal principal){

        String userName = principal.getName(); // Token se logged-in user ka naam mil jayega
        BorrowRecordResDto record = borrowService.borrowBookById(bookId,userName);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    //User ya Admin kitab return karega
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/return/{borrowId}")
    public ResponseEntity<BorrowRecordResDto> returnBookById(@PathVariable Long borrowId){
         BorrowRecordResDto record = borrowService.returnBookById(borrowId);
         return new ResponseEntity<>(record,HttpStatus.OK);
    }

    // login user apna history dekh stka hai
     @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/my-history")
    public ResponseEntity<List<BorrowRecordResDto>> getMyHistory(Principal principal){
          String userName = principal.getName();
          List<BorrowRecordResDto> history = borrowService.getMyHistory(userName);
          return new ResponseEntity<>(history,HttpStatus.OK);
     }


     // admin sare users ka borrow records dekhega
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-records")
    public ResponseEntity<List<BorrowRecordResDto>> getAllRecords(){
          List<BorrowRecordResDto> allRecords= borrowService.getAllRecords();
          return new ResponseEntity<>(allRecords,HttpStatus.OK);
    }



    }
