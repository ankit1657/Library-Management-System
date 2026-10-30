package com.LearningSpring.LearningAPI.Repository;

import com.LearningSpring.LearningAPI.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<BookEntity,Long> {
}
