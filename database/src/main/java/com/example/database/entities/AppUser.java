package com.example.database.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "AppUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user")
public class AppUser {
    @Id
    private UUID appUserId;

    @Column(nullable = false, length = 64)
    private String firstName;

    @Column(nullable = false, length = 64)
    private String lastName;

    @Column(length = 1024)
    private String about;

    @Column(unique = true, nullable = false)
    private String email;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant joinedAt;
}
