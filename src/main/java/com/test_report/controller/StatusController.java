package com.test_report.controller;

import com.test_report.dto.Message;
import com.test_report.dto.StatusDto;
import com.test_report.entity.ReportEntity;
import com.test_report.entity.StatusEntity;
import com.test_report.repository.StatusRepository;
import com.test_report.security.entity.UserEntity;
import com.test_report.security.jwt.JwtService;
import com.test_report.security.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/status")
@CrossOrigin(origins = "*")
public class StatusController {
    @Autowired
    StatusRepository statusRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JwtService jwtService;
    
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> list() {
        List<StatusEntity> listStatus = statusRepository.findAll();
        return new ResponseEntity<>(listStatus, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        if (!statusRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Status solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        StatusEntity user = statusRepository.findById(id).orElse(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> create(@RequestBody StatusDto statusDto, HttpServletRequest request) {
        StatusEntity status = new StatusEntity();
        if(!statusRepository.findByName(statusDto.getName()).isEmpty()){
            return new ResponseEntity<>(new Message("El Statusname ya existe."), HttpStatus.BAD_REQUEST);
        }
        status.setName(statusDto.getName());
        String jwtToken = request.getHeader("Authorization").substring(7); // Obtener el token del encabezado Authorization
        Claims claims = jwtService.getAllClaims(jwtToken);
        String subject = claims.getSubject();
        UserEntity createdBy = userRepository.findByUsername(subject).orElse(null);
        status.setCreatedBy(createdBy);
        status.setCreationDate(new Date());
        status.setModifiedBy(createdBy);
        status.setLastUpdateDate(new Date());
        statusRepository.save(status);
        return new ResponseEntity<>(new Message("Status creado exitosamente."), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody StatusDto statusDto, HttpServletRequest request) {
        if (!statusRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Status solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        if(!statusRepository.findByName(statusDto.getName()).isEmpty()){
            return new ResponseEntity<>(new Message("El Statusname ya existe."), HttpStatus.BAD_REQUEST);
        }
        StatusEntity status = statusRepository.findById(id).orElse(null);
        status.setName(statusDto.getName());
        String jwtToken = request.getHeader("Authorization").substring(7); // Obtener el token del encabezado Authorization
        Claims claims = jwtService.getAllClaims(jwtToken);
        String subject = claims.getSubject();
        UserEntity modifiedBy = userRepository.findByUsername(subject).orElse(null);
        status.setModifiedBy(modifiedBy);
        status.setLastUpdateDate(new Date());
        statusRepository.save(status);
        return new ResponseEntity<>(new Message("Status actualizado exitosamente."), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if (!statusRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Status solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        statusRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Status eliminado exitosamente."), HttpStatus.OK);
    }
}
