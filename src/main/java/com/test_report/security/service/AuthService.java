package com.test_report.security.service;

import com.test_report.security.dto.LoginDto;
import com.test_report.security.dto.TokenDto;
import com.test_report.security.dto.UserDto;
import com.test_report.security.entity.RolEntity;
import com.test_report.security.entity.UserEntity;
import com.test_report.security.jwt.JwtService;
import com.test_report.security.repository.RolRepository;
import com.test_report.security.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenDto login(LoginDto request) {
        UserDetails user = userRepository.findByUsername(request.getUser()).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return new TokenDto(jwtService.getToken(user));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public TokenDto register(UserDto request) {
        UserEntity user = new UserEntity();
        RolEntity rol = rolRepository.findByName("CUSTOMER").orElse(null);

        user.setId(userRepository.findMaxId() + 1);
        user.setUsername(request.getUsername());
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);
        user.setEmail(request.getEmail());
        user.setRol(rol);
        userRepository.save(user);

        return new TokenDto(jwtService.getToken(user));
    }
}
