package com.test_report.security.repository;

import com.test_report.security.entity.UserEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    //@Query("SELECT e FROM UserEntity e WHERE e.username = :username")
    //Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query("SELECT MAX(e.id) FROM UserEntity e")
    int findMaxId();
    
    public Optional<UserEntity> findByUsername(String username);
}
