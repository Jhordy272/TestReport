package com.test_report.controller;

import com.test_report.dto.Message;
import com.test_report.dto.ReportDto;
import com.test_report.entity.ProjectEntity;
import com.test_report.entity.ReportEntity;
import com.test_report.entity.StatusEntity;
import com.test_report.repository.ProjectRepository;
import com.test_report.repository.ReportRepository;
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
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    ReportRepository reportRepository;
    
    @Autowired
    ProjectRepository projectRepository;
    
    @Autowired
    StatusRepository statusRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JwtService jwtService;
    
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> list() {
        List<ReportEntity> listReports = reportRepository.findAll();
        return new ResponseEntity<>(listReports, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        if (!reportRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Usuario solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        ReportEntity report = reportRepository.findById(id).orElse(null);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> create(@RequestBody ReportDto reportDto, HttpServletRequest request) {
        ReportEntity report = new ReportEntity();
        report.setName(reportDto.getName());
        ProjectEntity project = projectRepository.findById(reportDto.getProject());
        report.setProject(project);
        StatusEntity status = statusRepository.findById(reportDto.getProject()).orElse(null);
        report.setStatus(status);
        report.setDetails(reportDto.getDetails());
        report.setExecutionDate(reportDto.getExecutionDate());
        String jwtToken = request.getHeader("Authorization").substring(7); // Obtener el token del encabezado Authorization
        Claims claims = jwtService.getAllClaims(jwtToken);
        String subject = claims.getSubject();
        UserEntity createdBy = userRepository.findByUsername(subject).orElse(null);
        report.setCreatedBy(createdBy);
        report.setCreationDate(new Date());
        report.setModifiedBy(createdBy);
        report.setLastUpdateDate(new Date());
        reportRepository.save(report);
        return new ResponseEntity<>(new Message("Reporte creado exitosamente."), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ReportDto reportDto, HttpServletRequest request) {
        if (!reportRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Reporte solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        ReportEntity report = reportRepository.findById(id).orElse(null);
        report.setName(reportDto.getName());
        ProjectEntity project = projectRepository.findById(reportDto.getProject());
        report.setProject(project);
        StatusEntity status = statusRepository.findById(reportDto.getProject()).orElse(null);
        report.setStatus(status);
        report.setDetails(reportDto.getDetails());
        report.setExecutionDate(reportDto.getExecutionDate());
        String jwtToken = request.getHeader("Authorization").substring(7); // Obtener el token del encabezado Authorization
        Claims claims = jwtService.getAllClaims(jwtToken);
        String subject = claims.getSubject();
        UserEntity modifiedBy = userRepository.findByUsername(subject).orElse(null);
        report.setModifiedBy(modifiedBy);
        report.setLastUpdateDate(new Date());
        reportRepository.save(report);
        return new ResponseEntity<>(new Message("Reporte actualizado exitosamente."), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if (!reportRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Reporte solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        reportRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Reporte eliminado exitosamente."), HttpStatus.OK);
    }
}
