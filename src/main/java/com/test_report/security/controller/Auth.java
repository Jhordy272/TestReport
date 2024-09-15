package com.test_report.security.controller;

import com.test_report.security.dto.LoginDto;
import com.test_report.security.dto.UserDto;
import com.test_report.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class Auth {
    
    @Autowired
    AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request){
        return new ResponseEntity<>(authService.login(request),HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto request){
        return new ResponseEntity<>(authService.register(request),HttpStatus.OK);
    }
    
}
