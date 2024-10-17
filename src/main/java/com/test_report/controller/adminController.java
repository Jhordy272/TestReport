package com.test_report.controller;

import com.test_report.security.entity.UserEntity;
import com.test_report.security.jwt.JwtService;
import com.test_report.security.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class adminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all_users")
    public ResponseEntity<?> getUsers(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").substring(7); // Obtener el token del encabezado Authorization
        Claims claims = jwtService.getAllClaims(jwtToken);
        System.out.println("Claims del token:");
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue()); 
        }
        List<UserEntity> listUsers = userRepository.findAll();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }
}
