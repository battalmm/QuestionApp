package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.request.CreateLikeRequest;
import com.korkmazyusufcan.questionapp.dto.response.LikeResponse;
import com.korkmazyusufcan.questionapp.entity.Like;
import com.korkmazyusufcan.questionapp.exception.ExceptionEntity;
import com.korkmazyusufcan.questionapp.exception.NotFoundException;
import com.korkmazyusufcan.questionapp.repository.LikeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    public LikeService(LikeRepository likeRepository,
                       UserService userService,
                       PostService postService) {

        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public List<LikeResponse> getAllLikesWithParameter(Optional<Long> postId, Optional<Long> userId) {
        List<Like> likeList;

        if (postId.isPresent())
        {
           likeList = likeRepository.findByPostId(postId.get());
        }
        else if (userId.isPresent())
        {
            likeList = likeRepository.findByUserId(userId.get());
        }
        else
        {
            likeList =  likeRepository.findAll();
        }

        return likeList
                .stream()
                .map(LikeResponse::new)
                .collect(Collectors.toList());
    }

    public LikeResponse getLikeById(Long likeId) {
        Like like = likeRepository.findById(likeId).orElseThrow(()-> new NotFoundException(ExceptionEntity.Like));

        return new LikeResponse(
                like.getId(),
                like.getPost().getId(),
                like.getUser().getId()
        );
    }

    public LikeResponse createLike(CreateLikeRequest postLikeRequest) {
        Like like = new Like(
                postService.findPostById(postLikeRequest.getPostId()),
                userService.findUserById(postLikeRequest.getUserId())
        );
        likeRepository.save(like);

        return new LikeResponse(
                like.getId(),
                like.getPost().getId(),
                like.getUser().getId()
        );
    }

    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }

}
