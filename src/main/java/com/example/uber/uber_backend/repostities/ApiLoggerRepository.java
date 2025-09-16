package com.example.uber.uber_backend.repostities;

import com.example.uber.uber_backend.entities.ApiLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiLoggerRepository extends JpaRepository<ApiLogger,Long> {
}
