package com.example.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "Activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue
    private UUID activityId;

    @ManyToOne
    @JoinColumn(name = "appUserId")
    private AppUser appUser;

    @Column(nullable = false)
    private Integer burntCalories;

    @Column(nullable = false)
    private Long time;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType type;
}
