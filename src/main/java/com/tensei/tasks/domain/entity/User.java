package com.tensei.tasks.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Column(nullable = false, unique = true, name = "username")
    String username;

    @Column(nullable = false, name = "password")
    String password;

    @Column(unique = true, name = "email")
    String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Task> tasks;

    @Column(nullable = false, updatable = false, name = "register_at")
    LocalDateTime registerAt;

    @PrePersist
    protected void created() {
        this.registerAt = LocalDateTime.now();
    }

}
