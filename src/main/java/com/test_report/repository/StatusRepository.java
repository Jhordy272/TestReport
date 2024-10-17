package com.test_report.repository;

import com.test_report.entity.StatusEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer>{
    public StatusEntity findById(int id);
    public Optional<StatusEntity> findByStatusName(String statusName);
}
