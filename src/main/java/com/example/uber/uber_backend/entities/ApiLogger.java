package com.example.uber.uber_backend.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_logger")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ApiLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String method;

    private String body;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_id")
    @CreatedBy
    private Long userId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
