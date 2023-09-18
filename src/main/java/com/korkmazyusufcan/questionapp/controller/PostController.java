package com.korkmazyusufcan.questionapp.controller;

import com.korkmazyusufcan.questionapp.dto.PostDto;
import com.korkmazyusufcan.questionapp.dto.request.PostCreateRequest;
import com.korkmazyusufcan.questionapp.dto.request.PostUpdateRequest;
import com.korkmazyusufcan.questionapp.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequest postCreateRequest){
        return ResponseEntity.ok(postService.postCreate(postCreateRequest));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest){
        return ResponseEntity.ok(postService.updatePost(postId,postUpdateRequest));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable Long postId){
        postService.deletePostById(postId);
        return ResponseEntity.ok("Post deleted");
    }
}
