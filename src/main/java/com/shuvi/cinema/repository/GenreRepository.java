package com.shuvi.cinema.repository;

import com.shuvi.cinema.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

/**
 * @author Shuvi
 */
public interface GenreRepository extends JpaRepository<GenreEntity, UUID> {

    Set<GenreEntity> findByIdIn(Set<UUID> uuids);
}
