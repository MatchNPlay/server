package com.matchnplay.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Schema(description = "User profile information")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Unique identifier of the user", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Username chosen by the user", example = "john_doe", required = true)
    private String username;

    @Column(nullable = false, unique = true)
    @Schema(description = "Email address of the user", example = "john@example.com", required = true)
    private String email;

    @Column(nullable = false)
    @Schema(description = "Hashed password of the user", example = "hashedpassword123", required = true)
    private String password;

    @Column(name = "fullname", nullable = false)
    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    private String fullname;

    @Column(name = "phone", nullable = false)
    @Schema(description = "Phone number of the user", example = "+1234567890", required = true)
    private String phone;

    @Column(name = "email_verified", nullable = false)
    @Schema(description = "Indicates if the user's email is verified", example = "false", required = true)
    private boolean emailVerified;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @Schema(description = "Timestamp when the user was created", example = "2025-05-18T10:15:30", required = true)
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Schema(description = "Timestamp when the user was last updated", example = "2025-05-18T10:15:30", required = true)
    private Instant updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens = new ArrayList<>();

}