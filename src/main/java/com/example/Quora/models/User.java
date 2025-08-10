package com.example.Quora.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String email;

    private String bio;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore  // Prevent recursion here
    private List<Question> questions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore  // Prevent recursion here
    private Set<Answer> answers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Comment> comments;
}
