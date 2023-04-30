package com.shuvi.cinema.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cinemas")
public class CinemaEntity {

    @Id
    @Column(length = 16, unique = true, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(length = 255, unique = true, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String description;

    @Column(nullable = false)
    private long duration;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cinemas_genres",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreEntity> genres;
}
