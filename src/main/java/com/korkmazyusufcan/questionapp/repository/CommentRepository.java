package com.korkmazyusufcan.questionapp.repository;

import com.korkmazyusufcan.questionapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByUserId(Long userId);

    //TODO
    // CREATE RELATED QUERY EVERY USERS COMMENTS TO SELECTED POST
    List<Comment> findPostCommentByUserId(Long postId);
}
