package com.example.uber.uber_backend.repostities;

import com.example.uber.uber_backend.entities.ErrorLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLoggerRepository extends JpaRepository<ErrorLogger,Long> {
}
