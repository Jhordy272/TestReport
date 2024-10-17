package com.test_report.entity;

import com.test_report.security.entity.UserEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reports")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "report_name")
    private String reportName;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_project", referencedColumnName = "id")
    private ProjectEntity idProject;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_status", referencedColumnName = "id")
    private StatusEntity status;
    
    @Column(name = "details")
    private String details;
    
    @Column(name = "execution_date")
    private Date execution_date;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private UserEntity createdBy;
    
    @Column(name = "creation_date")
    private Date creationDate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private UserEntity modifiedBy;
    
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
}
    
