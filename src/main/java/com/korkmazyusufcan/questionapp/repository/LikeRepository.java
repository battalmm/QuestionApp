package com.korkmazyusufcan.questionapp.repository;

import com.korkmazyusufcan.questionapp.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long > {
    List<Like> findByPostId(Long postId);
    List<Like> findByUserId(Long userId);
}
