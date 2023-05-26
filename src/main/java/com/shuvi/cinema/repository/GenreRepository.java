package com.shuvi.cinema.repository;

import com.shuvi.cinema.entity.GenreEntity;
import io.micrometer.core.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Shuvi
 */
public interface GenreRepository extends JpaRepository<GenreEntity, UUID> {

    List<GenreEntity> findByIdIn(@NonNull List<UUID> uuids);
}
