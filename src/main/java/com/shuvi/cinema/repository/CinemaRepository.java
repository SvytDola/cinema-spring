package com.shuvi.cinema.repository;

import com.shuvi.cinema.entity.CinemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

/**
 * @author Shuvi
 */
public interface CinemaRepository extends
        JpaRepository<CinemaEntity, UUID>,
        PagingAndSortingRepository<CinemaEntity, UUID> {

}
