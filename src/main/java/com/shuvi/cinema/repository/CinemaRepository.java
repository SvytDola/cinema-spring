package com.shuvi.cinema.repository;

import com.shuvi.cinema.entity.CinemaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Shuvi
 */
public interface CinemaRepository extends
        JpaRepository<CinemaEntity, UUID> {
    List<CinemaEntity> findByGenresNameIn(List<String> genres, Pageable pageable);
}
