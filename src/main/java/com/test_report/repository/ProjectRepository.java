package com.test_report.repository;

import com.test_report.entity.ProjectEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer>{
    public Optional<ProjectEntity> findById(int id);
    public Optional<ProjectEntity> findByName(String name);
}
