package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.PostDto;
import com.korkmazyusufcan.questionapp.dto.request.PostCreateRequest;
import com.korkmazyusufcan.questionapp.dto.request.PostUpdateRequest;
import com.korkmazyusufcan.questionapp.entity.Post;
import com.korkmazyusufcan.questionapp.exception.ExceptionEntity;
import com.korkmazyusufcan.questionapp.exception.NotFoundException;
import com.korkmazyusufcan.questionapp.mapper.PostMapper;
import com.korkmazyusufcan.questionapp.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository,
                       UserService userService,
                       PostMapper postMapper) {

        this.postRepository = postRepository;
        this.userService = userService;
        this.postMapper = postMapper;
    }


    public List<PostDto> getAllPosts(){

        return postMapper.toDtoList(postRepository.findAll());
    };

    public List<PostDto> getPostsByUser(Long userId) {
        Long checkedUserId = userService.findUserById(userId).getId();
        List<Post> postList = postRepository.findByUserId(checkedUserId);
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
        Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException(ExceptionEntity.Post));
        post.setText(postUpdateRequest.getText());
        return postMapper.toDto(postRepository.save(post));
    }

    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    protected Post findPostById(Long postId){
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ExceptionEntity.Post));
    }
}
