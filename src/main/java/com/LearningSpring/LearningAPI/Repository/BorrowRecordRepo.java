package com.LearningSpring.LearningAPI.Repository;

import com.LearningSpring.LearningAPI.entity.BorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRecordRepo extends JpaRepository<BorrowEntity,Long> {
    List<BorrowEntity> findByUserId(Long id);
}
