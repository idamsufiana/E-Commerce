package com.makeup.auth.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name; // USER, ADMIN
}