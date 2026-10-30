package com.LearningSpring.LearningAPI.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "borrow")

public class BorrowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate borrowDate;// Kitab lene ki date

    @Column(nullable = false)
    private LocalDate dueDate;// Wapas karne ki deadline


    private LocalDate returnDate;// Asli mein kab wapas kiya

    @Column(nullable = false)
    private String status ;// "BORROWED" ya "RETURNED"

    // Kis user ne kitab li hai
    //bahut sare records ek user ke ho skte hai
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;

    // Kaun si kitab li gai hai
    // bahut sare records ek book ka ho skta hai
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id",nullable = false)
    private BookEntity book;
}
