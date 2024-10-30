package com.test_report.controller;

import com.test_report.dto.Message;
import com.test_report.security.dto.UserDto;
import com.test_report.security.entity.RolEntity;
import com.test_report.security.entity.UserEntity;
import com.test_report.security.repository.RolRepository;
import com.test_report.security.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RolRepository rolRepository;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> list() {
        List<UserEntity> listUsers = userRepository.findAll();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Usuario solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        UserEntity user = userRepository.findById(id).orElse(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        UserEntity user = new UserEntity();
        if(!userRepository.findByUsername(userDto.getUsername()).isEmpty()){
            return new ResponseEntity<>(new Message("El Username ya existe."), HttpStatus.BAD_REQUEST);
        }
        user.setUsername(userDto.getUsername());
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(hashedPassword);
        user.setEmail(userDto.getEmail());
        RolEntity rol = rolRepository.findById(userDto.getIdRol()).orElse(null);
        user.setRol(rol);
        userRepository.save(user);
        return new ResponseEntity<>(new Message("Usuario creado exitosamente."), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody UserDto userDto) {
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Usuario solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        if(!userRepository.findByUsername(userDto.getUsername()).isEmpty()){
            return new ResponseEntity<>(new Message("El Username ya existe."), HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userRepository.findById(id).orElse(null);
        user.setUsername(userDto.getUsername());
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(hashedPassword);
        user.setEmail(userDto.getEmail());
        RolEntity rol = rolRepository.findById(userDto.getIdRol()).orElse(null);
        user.setRol(rol);
        userRepository.save(user);
        return new ResponseEntity<>(new Message("Usuario actualizado exitosamente."), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Usuario solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Usuario eliminado exitosamente."), HttpStatus.OK);
    }
}
