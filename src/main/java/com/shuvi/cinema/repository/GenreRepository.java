package com.shuvi.cinema.repository;

import com.shuvi.cinema.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

/**
 * @author Shuvi
 */
@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, UUID> {

    Set<GenreEntity> findByIdIn(Set<UUID> uuids);
}
