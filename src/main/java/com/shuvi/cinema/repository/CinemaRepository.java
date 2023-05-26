package com.shuvi.cinema.repository;

import com.shuvi.cinema.entity.CinemaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.UUID;

/**
 * @author Shuvi
 */
public interface CinemaRepository extends JpaRepository<CinemaEntity, UUID>, QuerydslPredicateExecutor<CinemaEntity> {
    List<CinemaEntity> findByGenresNameIn(List<String> genres, Pageable pageable);
}
