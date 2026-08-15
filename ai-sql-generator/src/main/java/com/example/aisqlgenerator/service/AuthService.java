package com.example.aisqlgenerator.service;

import com.example.aisqlgenerator.dto.LoginRequest;
import com.example.aisqlgenerator.dto.RegisterRequest;
import com.example.aisqlgenerator.model.User;
import com.example.aisqlgenerator.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        String hashedPassword =
                passwordEncoder.encode(request.getPassword());

        userRepository.save(
                request.getUsername(),
                request.getEmail(),
                hashedPassword
        );
    }

    public String login(LoginRequest request) {

        User user =
                userRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Invalid email or password"
                                )
                        );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        return jwtService.generateToken(user.getId());
    }
}