package com.shuvi.cinema.repository;

import com.shuvi.cinema.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Shuvi
 */
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
}
