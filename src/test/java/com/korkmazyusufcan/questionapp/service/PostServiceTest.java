package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.PostDto;
import com.korkmazyusufcan.questionapp.dto.request.PostCreateRequest;
import com.korkmazyusufcan.questionapp.entity.Post;
import com.korkmazyusufcan.questionapp.entity.user.User;
import com.korkmazyusufcan.questionapp.exception.NotFoundException;
import com.korkmazyusufcan.questionapp.mapper.PostMapper;
import com.korkmazyusufcan.questionapp.repository.PostRepository;
import com.korkmazyusufcan.questionapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import supports.TestSupport;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class PostServiceTest extends TestSupport {

    private PostRepository postRepository;
    private UserService userService;
    private PostMapper postMapper;


    private PostService postService;
    @BeforeEach
    public void setUp(){
        userService = mock(UserService.class);
        postMapper = mock(PostMapper.class);
        postRepository = mock(PostRepository.class);

        postService = new PostService(postRepository,userService,postMapper);

    }

    @Test
    public void getAllPosts_ShouldReturnPostDtoList(){

        Post post = generatePost();
        List<Post> postList = generatePostList(post);
        PostDto postDto = generatePostDto(post);
        List<PostDto> expected = generatePostDtoList(postDto);

        when(postRepository.findAll()).thenReturn(postList);
        when(postMapper.toDtoList(postList)).thenReturn(expected);

        List<PostDto> result = postService.getAllPosts();

        assertEquals(expected,result);
    }

    @Test
    public void getPostsByUser_whenUserIdRelatedPostsAreExist_shouldReturnPostDtoList(){
        Long userId = DEFAULT_USER_ID;
        User user = generateUser();
        Post post = generatePost();
        List<Post> postList = generatePostList(post);
        PostDto postDto = generatePostDto(post);
        List<PostDto> expected = generatePostDtoList(postDto);

        when(postRepository.findByUserId(userId)).thenReturn(postList);
        when(userService.findUserById(userId)).thenReturn(user);
        when(postMapper.toDto(postList.get(0))).thenReturn(postDto);

        List<PostDto> result = postService.getPostsByUser(userId);

        assertEquals(expected,result);

        verify(userService,times(1)).findUserById(userId);
        verify(postRepository,times(1)).findByUserId(userId);
        verify(postMapper,times(1)).toDto(postList.get(0));
    }

    @Test
    public void getPostsByUser_whenGivenUserIdNotMatchAnyUser_shouldThrowNotFoundError() {
        Long userId = DEFAULT_USER_ID;
        User user = generateUser();

        when(userService.findUserById(userId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,()-> postService.getPostsByUser(userId));


    }

    //TODO
    // Check all methods
    @Test
    public void postCreate_whenPostCreateRequestIsValid_shouldReturnPostDto(){
        PostCreateRequest postCreateRequest = generatePostCreateRequest();
        User user = generateUser();
        Post newPost = Post.builder()
                .user(user)
                .text(postCreateRequest.getText())
                .build();

        PostDto expected = generatePostDto(newPost);

        when(userService.findUserById(postCreateRequest.getUserId())).thenReturn(user);
        when(postRepository.save(newPost)).thenReturn(newPost);
        when(postMapper.toDto(newPost)).thenReturn(expected);

        PostDto result = postService.postCreate(postCreateRequest);

        assertEquals(expected,result);

        verify(postRepository,times(1)).save(newPost);
        verify(userService,times(1)).findUserById(postCreateRequest.getUserId());
        verify(postMapper,times(1)).toDto(newPost);

    }

    @Test
    public void postCreate_whenPostCreateRequestIsNotValidForGivenUserId_shouldThrowNotFoundException(){

    }

    @Test
    public void updatePost_whenPostIdRelatedPostIsExistAndPostUpdateRequestIsValid_shouldReturnUpdatedPostInFormatOfPostDto(){

    }

    @Test
    public void updatePost_whenPostIdRelatedPostIsNotExistAndPostUpdateRequestIsValid_shouldThrowNotFoundException(){

    }

    //TODO
    // UpdatePost
    // Create Post

    @Test
    public void deletePostById_shouldExecuteOneTime(){
        postService.deletePostById(DEFAULT_POST_ID);

        verify(postRepository,times(1)).deleteById(DEFAULT_POST_ID);
    }

    @Test
    public void findPostById_whenPostIdRelatedPostIsExist_shouldThrowNotFoundException(){
        Post expected = generatePost();
        Long postId = expected.getId();

        when(postRepository.findById(postId)).thenReturn(Optional.of(expected));

        Post result = postService.findPostById(postId);

        assertEquals(expected,result);

    }
    @Test
    public void findPostById_whenPostIdRelatedPostIsNotExist_shouldThrowNotFoundException(){
        Long postId = DEFAULT_POST_ID;

        assertThrows(NotFoundException.class,()-> postService.findPostById(postId));
    }
}