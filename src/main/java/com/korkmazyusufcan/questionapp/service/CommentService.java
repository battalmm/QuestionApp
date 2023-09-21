package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.request.PostCommentRequest;
import com.korkmazyusufcan.questionapp.dto.request.UpdateCommentRequest;
import com.korkmazyusufcan.questionapp.dto.response.CommentResponse;
import com.korkmazyusufcan.questionapp.entity.Comment;
import com.korkmazyusufcan.questionapp.entity.User;
import com.korkmazyusufcan.questionapp.exception.ExceptionEntity;
import com.korkmazyusufcan.questionapp.exception.NotFoundException;
import com.korkmazyusufcan.questionapp.repository.CommentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository,
                          UserService userService,
                          PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<CommentResponse> getAllCommentsWithParameter(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> commentList;
        if (userId.isPresent())
        {
            commentList = commentRepository.findByUserId(userId.get());
        }
        else if (postId.isPresent())
        {
            commentList = commentRepository.findByPostId(postId.get());
        }
        else
        {
            commentList= commentRepository.findAll();
        }
        return commentList
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    public CommentResponse getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NotFoundException(ExceptionEntity.Comment));
        return new CommentResponse(comment);
    }

    public CommentResponse createComment(PostCommentRequest postCommentRequest) {
        Comment comment = new Comment(
                postService.findPostById(postCommentRequest.getPostId()),
                userService.findUserById(postCommentRequest.getUserId()),
                postCommentRequest.getText()
        );
        commentRepository.save(comment);

        return new CommentResponse(
                comment.getId(),
                comment.getUser().getName(),
                comment.getText()
        );
    }

    public void updateComment(Long commentId, UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(ExceptionEntity.Comment));
        comment.setText(updateCommentRequest.getText());
        commentRepository.save(comment);

    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
