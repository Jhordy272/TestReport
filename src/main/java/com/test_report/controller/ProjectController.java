package com.test_report.controller;

import com.test_report.dto.Message;
import com.test_report.dto.ProjectDto;
import com.test_report.entity.ProjectEntity;
import com.test_report.repository.ProjectRepository;
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
@RequestMapping("/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    
    @Autowired
    ProjectRepository projectRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JwtService jwtService;
    
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> list() {
        List<ProjectEntity> listProject = projectRepository.findAll();
        return new ResponseEntity<>(listProject, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        if (!projectRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Proyecto solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        ProjectEntity project = projectRepository.findById(id).orElse(null);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
    
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> create(@RequestBody ProjectDto projectDto, HttpServletRequest request) {
        ProjectEntity project = new ProjectEntity ();
        if(!projectRepository.findByName(projectDto.getName()).isEmpty()){
            return new ResponseEntity<>(new Message("El ProjectName ya existe."), HttpStatus.BAD_REQUEST);
        }
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        UserEntity manager = userRepository.findById(projectDto.getManager()).orElse(null);
        project.setManager(manager);
        String jwtToken = request.getHeader("Authorization").substring(7); // Obtener el token del encabezado Authorization
        Claims claims = jwtService.getAllClaims(jwtToken);
        String subject = claims.getSubject();
        UserEntity createdBy = userRepository.findByUsername(subject).orElse(null);
        project.setCreatedBy(createdBy);
        project.setCreationDate(new Date());
        project.setModifiedBy(createdBy);
        project.setLastUpdateDate(new Date());
        projectRepository.save(project);
        return new ResponseEntity<>(new Message("Proyecto creado exitosamente."), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProjectDto projectDto, HttpServletRequest request) {
        if (!projectRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Proyecto solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        ProjectEntity project = projectRepository.findById(id).orElse(null);
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        UserEntity manager = userRepository.findById(projectDto.getManager()).orElse(null);
        project.setManager(manager);
        String jwtToken = request.getHeader("Authorization").substring(7); // Obtener el token del encabezado Authorization
        Claims claims = jwtService.getAllClaims(jwtToken);
        String subject = claims.getSubject();
        UserEntity modifiedBy = userRepository.findByUsername(subject).orElse(null);
        project.setModifiedBy(modifiedBy);
        project.setLastUpdateDate(new Date());
        projectRepository.save(project);
        return new ResponseEntity<>(new Message("Proyecto actualizado exitosamente."), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if (!projectRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("El Proyecto solicitado no existe."), HttpStatus.NOT_FOUND);
        }
        projectRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Proyecto eliminado exitosamente."), HttpStatus.OK);
    }
    
}
