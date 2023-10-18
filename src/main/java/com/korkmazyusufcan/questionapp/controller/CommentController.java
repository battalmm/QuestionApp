package com.korkmazyusufcan.questionapp.controller;

import com.korkmazyusufcan.questionapp.dto.request.PostCommentRequest;
import com.korkmazyusufcan.questionapp.dto.request.UpdateCommentRequest;
import com.korkmazyusufcan.questionapp.dto.response.CommentResponse;
import com.korkmazyusufcan.questionapp.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(){
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/user")
    public List<CommentResponse> getAllCommentsByUserId(@RequestParam Long userId){
        return commentService.getAllCommentsByUserId(userId);
    }

    @GetMapping("/post")
    public List<CommentResponse> getAllCommentsByPostId(@RequestParam Long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.getCommentById(commentId));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody PostCommentRequest postCommentRequest){
        return ResponseEntity.ok(commentService.createComment(postCommentRequest));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId,
                                                @RequestBody UpdateCommentRequest updateCommentRequest){
        commentService.updateComment(commentId,updateCommentRequest);
        return ResponseEntity.ok("Comment Updated");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String>  deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment Deleted");
    }

}
