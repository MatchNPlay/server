package com.matchnplay.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "users")
@Schema(description = "User profile information")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "Username chosen by the user", example = "john_doe")
    private String username;

    @Schema(description = "Email address of the user", example = "john@example.com")
    private String email;

    @Column(name = "password_hash")
    @Schema(description = "Hashed password of the user", example = "hashedpassword123")
    private String passwordHash;

    @Column(name = "full_name")
    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Column(name = "skill_level")
    @Schema(description = "Skill level of the user", example = "Beginner")
    private String skillLevel;

    @Schema(description = "Phone number of the user", example = "+1234567890")
    private String phone;

    @Column(name = "created_at")
    @Schema(description = "Timestamp when the user was created", example = "2025-05-18T10:15:30")
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getSkillLevel() { return skillLevel; }
    public void setSkillLevel(String skillLevel) { this.skillLevel = skillLevel; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}