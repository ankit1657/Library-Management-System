package com.LearningSpring.LearningAPI.Service;

import com.LearningSpring.LearningAPI.dto.UserReqDto;
import com.LearningSpring.LearningAPI.dto.UserResDto;
import com.LearningSpring.LearningAPI.entity.UserEntity;
import com.LearningSpring.LearningAPI.Exception.ResourceNotFoundException;
import com.LearningSpring.LearningAPI.Repository.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class UserService {

      private final UserRepo userRepo;
      private final PasswordEncoder passwordEncoder;


    @CacheEvict(value = "users", allEntries = true)
    public UserResDto registerUser(@Valid UserReqDto reqDto) {
        UserEntity user = new UserEntity();
         user.setUserName(reqDto.getUserName());
         user.setPassword( passwordEncoder.encode( reqDto.getPassword())); // password encod
         user.setEmail(reqDto.getEmail());
         user.setAge(reqDto.getAge());

         // agar role nhi diya gya hai to default role user kr dete hai
         if(reqDto.getRole() == null || reqDto.getRole().isEmpty()){
              user.setRole("ROLE_USER");
         }else{
             user.setRole(reqDto.getRole());
         }

         UserEntity savedUser = userRepo.save(user);
         return mapToDto(savedUser);
    }

    private UserResDto mapToDto(UserEntity user) {
        return new UserResDto(
                 user.getId(),
                user.getUserName(),
//                user.getPassword(),
                user.getEmail(),
                user.getAge(),
                user.getRole()
        );
    }

    @Cacheable(value = "users" , key = "#id")
    public UserResDto getUserById(Long id, String currUserName) {

        UserEntity currentUser = userRepo.findByUserName(currUserName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //Agar user ADMIN nahi hai, aur wo kisi doosre ki ID access kar raha hai
        if (!currentUser.getRole().equals("ROLE_ADMIN") && !currentUser.getId().equals(id)) {
            throw new RuntimeException("Access Denied! You can only view your own profile.");
        }

        UserEntity user = userRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id"+id));
        return mapToDto(user);

    }

    @Cacheable(value = "users")
    public List<UserResDto> getAllUsers(long l) {

        List<UserEntity> usersList = userRepo.findAll();
        return usersList.stream()
                .map(this::mapToDto)
                .toList();
    }
}
