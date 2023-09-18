package com.korkmazyusufcan.questionapp.controller;

import com.korkmazyusufcan.questionapp.dto.LikeDto;
import com.korkmazyusufcan.questionapp.dto.response.LikeResponse;
import com.korkmazyusufcan.questionapp.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

    private  final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<LikeResponse>> getAllLikes(@RequestBody Optional<Long> postId,
                                                          @RequestBody Optional<Long> userId){
        return ResponseEntity.ok(likeService.getAllLikesWithParameter(postId,userId));
    }

    @GetMapping("/{likeId}")
    public ResponseEntity<LikeDto> getLikeById(@PathVariable Long likeId){
        return ResponseEntity.ok(likeService.getLikeById(likeId));
    }

    @PostMapping
    public ResponseEntity<String> createLike(@RequestBody PostLikeRequest postLikeRequest){
        likeService.createLike(postLikeRequest);
        return ResponseEntity.ok("Like created");
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long likeId){
        likeService.deleteLike(likeId);
        return ResponseEntity.ok("Like removed");
    }
}
