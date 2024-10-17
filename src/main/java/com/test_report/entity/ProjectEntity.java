package com.test_report.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "project_name")
    private String projectName;
    
    
}
