package com.test_report.security.repository;

import com.test_report.security.entity.RolEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<RolEntity, Integer>{
    public Optional<RolEntity> findById(int id);
    public Optional<RolEntity> findByName(String name);
}
