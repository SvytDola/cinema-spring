package com.shuvi.cinema.entity;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

import java.util.UUID;

/**
 * @author Shuvi
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class GenreEntity {
    @Id
    @Column(length = 16, unique = true, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}
