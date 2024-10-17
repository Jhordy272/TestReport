package com.test_report.repository;

import com.test_report.entity.ReportEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer>{
    public ReportEntity findById(int id);
    public Optional<ReportEntity> findByReportName(String reportName);
}
