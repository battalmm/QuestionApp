package com.korkmazyusufcan.questionapp.repository;

import com.korkmazyusufcan.questionapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
