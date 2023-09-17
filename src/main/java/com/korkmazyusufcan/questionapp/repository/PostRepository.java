package com.korkmazyusufcan.questionapp.repository;

import com.korkmazyusufcan.questionapp.entity.Like;
import com.korkmazyusufcan.questionapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
}
