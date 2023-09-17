package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.PostDto;
import com.korkmazyusufcan.questionapp.entity.Post;
import com.korkmazyusufcan.questionapp.mapper.PostMapper;
import com.korkmazyusufcan.questionapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final LikeService likeService;
    private final CommentService commentService;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository,
                       LikeService likeService,
                       CommentService commentService,
                       PostMapper postMapper) {

        this.postRepository = postRepository;
        this.likeService = likeService;
        this.commentService = commentService;
        this.postMapper = postMapper;
    }


    public List<PostDto> getPostsByUser(Long userId) {
        //TODO
        // Check if user don't exist
        List<Post> postList = postRepository.findByUserId(userId);

        return postList
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> getAllPosts(){
        return postMapper.toDtoList(postRepository.findAll());
    };
}
