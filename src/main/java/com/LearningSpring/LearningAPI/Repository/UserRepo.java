package com.LearningSpring.LearningAPI.Repository;

import com.LearningSpring.LearningAPI.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Long> {
   Optional<UserEntity > findByUserName(String userName);
}
