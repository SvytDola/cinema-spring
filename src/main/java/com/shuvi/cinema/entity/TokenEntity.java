package com.shuvi.cinema.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * Сущность "Token".
 *
 * @author Danila Abdullin
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
public class TokenEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    public String token;

    @Column(nullable = false)
    public boolean revoked;

    @Column(nullable = false)
    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

}
