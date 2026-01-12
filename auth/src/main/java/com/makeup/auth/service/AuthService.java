package com.makeup.auth.service;

import com.makeup.auth.dto.RegisterRequest;
import com.makeup.auth.model.Role;
import com.makeup.auth.model.User;
import com.makeup.auth.repository.RoleRepository;
import com.makeup.auth.repository.UserRepository;
import com.makeup.auth.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtProvider.generateToken(user);
    }

    public void register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        User user = new User();
        user.setEmail(req.getEmail());
        user.setName(req.getName());
        user.setPasswordHash(
                passwordEncoder.encode(req.getPassword())
        );
        user.getRoles().add(userRole);

        userRepository.save(user);
    }
}