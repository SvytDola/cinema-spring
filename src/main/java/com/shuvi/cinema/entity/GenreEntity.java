package com.shuvi.cinema.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;
}
