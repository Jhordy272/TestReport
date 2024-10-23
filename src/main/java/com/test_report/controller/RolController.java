package com.test_report.controller;

import com.test_report.dto.Message;
import com.test_report.security.dto.RolDto;
import com.test_report.security.entity.RolEntity;
import com.test_report.security.entity.UserEntity;
import com.test_report.security.jwt.JwtService;
import com.test_report.security.repository.RolRepository;
import com.test_report.security.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RolController {
    
    @Autowired
    RolRepository rolRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JwtService jwtService;
    
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> list() {
        List<RolEntity> listUsers = rolRepository.findAll();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        if (!rolRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Rol solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        RolEntity rol = rolRepository.findById(id).orElse(null);
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }
    
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> create(@RequestBody RolDto rolDto, HttpServletRequest request) {
        RolEntity rol = new RolEntity();
        if(!rolRepository.findByName(rolDto.getName()).isEmpty()){
            return new ResponseEntity<>(new Message("El Rolname ya existe."), HttpStatus.BAD_REQUEST);
        }
        rol.setName(rolDto.getName());
        String jwtToken = request.getHeader("Authorization").substring(7); // Obtener el token del encabezado Authorization
        Claims claims = jwtService.getAllClaims(jwtToken);
        String subject = claims.getSubject();
        UserEntity createdBy = userRepository.findByUsername(subject).orElse(null);
        rol.setCreatedBy(createdBy);
        rol.setCreationDate(new Date());
        rol.setModifiedBy(createdBy);
        rol.setLastUpdateDate(new Date());
        rolRepository.save(rol);
        return new ResponseEntity<>(new Message("Rol creado exitosamente."), HttpStatus.OK);
    }

}
