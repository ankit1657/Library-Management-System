package com.LearningSpring.LearningAPI.Controller;


import com.LearningSpring.LearningAPI.dto.AuthResDto;
import com.LearningSpring.LearningAPI.dto.LoginReqDto;
import com.LearningSpring.LearningAPI.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@RequestBody LoginReqDto loginReqDto) {
        // 1. Username aur password verify karna
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReqDto.getUserName(), loginReqDto.getPassword())
        );

        // 2. Agar sahi hai toh Token bana kar return karein
        if (authenticate.isAuthenticated()) {
            String token = jwtService.generateToken(loginReqDto.getUserName());
            return ResponseEntity.ok(new AuthResDto(token));
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }


}
