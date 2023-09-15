package com.korkmazyusufcan.questionapp.repository;

import com.korkmazyusufcan.questionapp.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long > {
}
