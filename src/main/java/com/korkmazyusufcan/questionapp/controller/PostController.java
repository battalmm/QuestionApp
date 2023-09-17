package com.korkmazyusufcan.questionapp.controller;

import com.korkmazyusufcan.questionapp.dto.PostDto;
import com.korkmazyusufcan.questionapp.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return  ResponseEntity.ok(postService.getAllPosts());
    }
}
