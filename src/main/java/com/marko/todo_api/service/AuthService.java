package com.marko.todo_api.service;

import com.marko.todo_api.dto.AuthRequestDTO;
import com.marko.todo_api.dto.AuthResponseDTO;
import com.marko.todo_api.model.User;
import com.marko.todo_api.repository.UserRepository;
import com.marko.todo_api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO register(AuthRequestDTO request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        User savedUser=userRepository.save(user);
        String token= jwtService.generateToken(savedUser);
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(AuthRequestDTO request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user=userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }

}
