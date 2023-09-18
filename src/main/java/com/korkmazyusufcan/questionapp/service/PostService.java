package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.PostDto;
import com.korkmazyusufcan.questionapp.dto.request.PostCreateRequest;
import com.korkmazyusufcan.questionapp.dto.request.PostUpdateRequest;
import com.korkmazyusufcan.questionapp.entity.Post;
import com.korkmazyusufcan.questionapp.mapper.PostMapper;
import com.korkmazyusufcan.questionapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository,
                       UserService userService,
                       LikeService likeService,
                       CommentService commentService,
                       PostMapper postMapper) {

        this.postRepository = postRepository;
        this.userService = userService;
        this.likeService = likeService;
        this.commentService = commentService;
        this.postMapper = postMapper;
    }


    public List<PostDto> getAllPosts(){

        return postMapper.toDtoList(postRepository.findAll());
    };

    public List<PostDto> getPostsByUser(Long userId) {
        //TODO
        // POST NOT FOUND CASE SHOULD BE IMPLEMENT
        List<Post> postList = postRepository.findByUserId(userId);

        return postList
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public PostDto postCreate(PostCreateRequest postCreateRequest) {
        Post newPost = new Post(
                userService.findUserById(postCreateRequest.getUserId()),
                postCreateRequest.getText()
        );

        return postMapper.toDto( postRepository.save(newPost));
    }

    public PostDto updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        //TODO
        // POST NOT FOUND CASE SHOULD BE IMPLEMENT
        Post post = postRepository.findById(postId).orElseThrow();
        post.setText(postUpdateRequest.getText());
        return postMapper.toDto(postRepository.save(post));
    }

    public void deletePostById(Long postId) {
        //TODO
        // POST NOT FOUND CASE SHOULD BE IMPLEMENT
        postRepository.deleteById(postId);
    }
}
