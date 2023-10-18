package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.request.PostCommentRequest;
import com.korkmazyusufcan.questionapp.dto.request.UpdateCommentRequest;
import com.korkmazyusufcan.questionapp.dto.response.CommentResponse;
import com.korkmazyusufcan.questionapp.entity.Comment;
import com.korkmazyusufcan.questionapp.entity.Post;
import com.korkmazyusufcan.questionapp.entity.user.User;
import com.korkmazyusufcan.questionapp.exception.ExceptionEntity;
import com.korkmazyusufcan.questionapp.exception.NotFoundException;
import com.korkmazyusufcan.questionapp.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supports.CommentTestSupport;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest extends CommentTestSupport {

    private CommentService commentService;
    private  CommentRepository commentRepository;
    private  UserService userService;
    private  PostService postService;

    @BeforeEach
    void setUp(){
        commentRepository = mock(CommentRepository.class);
        userService = mock(UserService.class);
        postService = mock(PostService.class);

        commentService = new CommentService(commentRepository,userService,postService);
    }

    @Test
    public void getAllComments_ShouldReturnCommentResponsesList(){

        List<Comment> commentList = generateCommentList();
        List<CommentResponse> expected = generateCommentResponseList(commentList.get(0));

        when(commentRepository.findAll()).thenReturn(commentList);

        List<CommentResponse> result = commentService.getAllComments();

        assertEquals(expected,result);

        verify(commentRepository,times(1)).findAll();
    }

    @Test
    public void getAllCommentsByUserId_whenUserExistWithGivenUserId_shouldReturnListOfCommentResponse(){

        List<Comment> userComments = generateCommentList();
        List<CommentResponse> expected = generateCommentResponseList(userComments.get(0));

        when(userService.findUserById(5L)).thenReturn(generateUser());
        when(commentRepository.findByUserId(5L)).thenReturn(userComments);

        List<CommentResponse> result = commentService.getAllCommentsByUserId(5L);

        assertEquals(expected,result);

        verify(userService,times(1)).findUserById(5L);
        verify(commentRepository,times(1)).findByUserId(5L);
    }

    @Test
    public void getAllCommentsByUserId_whenUserNotExistWithGivenUserId_shouldThrowNotFoundException(){

        when(userService.findUserById(any(Long.class))).thenThrow(new NotFoundException(ExceptionEntity.User));

        assertThrows(NotFoundException.class, () -> commentService.getAllCommentsByUserId(111L));

        verify(userService, times(1)).findUserById(111L);
        verify(commentRepository, times(0)).findByUserId(111L);
    }

    @Test
    public void getAllCommentsByPostId_whenPostExistWithGivenId_shouldReturnListCommentResponse(){

        List<Comment> commentList = generateCommentList();
        Post post = generatePost();
        List<CommentResponse> expected = generateCommentResponseList(commentList.get(0));

        when(commentRepository.findByPostId(post.getId())).thenReturn(commentList);
        when(postService.findPostById(post.getId())).thenReturn(post);

        List<CommentResponse> result = commentService.getAllCommentsByPostId(post.getId());

        assertEquals(expected,result);

        verify(commentRepository,times(1)).findByPostId(post.getId());
        verify(postService,times(1)).findPostById(post.getId());
    }

    @Test
    public void getAllCommentsByPostId_whenPostNotExistWithGivenId_shouldThrowNotFoundException(){

        when(postService.findPostById(1L)).thenThrow(new NotFoundException(ExceptionEntity.User));

        assertThrows(NotFoundException.class,()->postService.findPostById(1L));

        verify(postService,times(1)).findPostById(1L);
        verify(commentRepository,times(0)).findByPostId(1L);
    }

    @Test
    public void getCommentById_whenCommentExistWithGivenId_shouldReturnCommentResponse(){

        Comment comment = generateComment();
        CommentResponse expected = generateCommentResponse(comment);

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        CommentResponse result = commentService.getCommentById(comment.getId());

        assertEquals(expected,result);

        verify(commentRepository,times(1)).findById(comment.getId());
    }

    @Test
    public void getCommentById_whenCommentNotExistWithGivenId_shouldReturnNotFoundException(){

        assertThrows(NotFoundException.class,()-> commentService.getCommentById(1L));
    }

    @Test
    public void createComment_whenPostCommentRequestIsValidAndIdRelatedPostIsExistAndIdRelatedUserIsExist_shouldReturnCommentResponse(){

        PostCommentRequest postCommentRequest = generatePostCommentRequest();
        User validUserForPostCommentRequest = generateUser();
        Post validPostForPostCommentRequest = generatePost();
        Comment comment = new Comment(
                validPostForPostCommentRequest,
                validUserForPostCommentRequest,
                postCommentRequest.getText()
        );
        CommentResponse expected = generateCommentResponse(comment);

        when(postService.findPostById(postCommentRequest.getPostId())).thenReturn(validPostForPostCommentRequest);
        when(userService.findUserById(postCommentRequest.getUserId())).thenReturn(validUserForPostCommentRequest);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentResponse result = commentService.createComment(postCommentRequest);

        assertEquals(expected,result);

        verify(postService, times(1)).findPostById(validPostForPostCommentRequest.getId());
        verify(userService, times(1)).findUserById(validUserForPostCommentRequest.getId());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void createComment_whenPostCommentRequestIsValidAndIdRelatedPostIsExistButIdRelatedUserNotExist_shouldThrowNotFoundException(){

        PostCommentRequest postCommentRequest = generatePostCommentRequest();

        when(userService.findUserById(any(Long.class))).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,()-> commentService.createComment(postCommentRequest));

        verify(postService,times(1)).findPostById(any(Long.class));
        verify(userService,times(1)).findUserById(any(Long.class));
    }

    @Test
    public void createComment_whenPostCommentRequestIsValidAndIdRelatedUserIsExistButIdRelatedPostNotExist_shouldThrowNotFoundException(){
        PostCommentRequest postCommentRequest = generatePostCommentRequest();

        when(postService.findPostById(any(Long.class))).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,()-> commentService.createComment(postCommentRequest));

        verify(userService,times(0)).findUserById(any(Long.class));
        verify(postService,times(1)).findPostById(any(Long.class));
    }

    @Test
    public void updateComment_whenCommentIdRelatedCommentIsExistAndUpdateCommentRequestIsValid_shouldUpdateComment(){

        Long commentId = DEFAULT_COMMENT_ID;
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest("update-comment-request-text");
        Comment comment = generateComment();
        Comment expected = updateComment(comment,updateCommentRequest);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(expected)).thenReturn(expected);

        commentService.updateComment(comment.getId(),updateCommentRequest);

        assertEquals(expected.getText(),comment.getText());

        verify(commentRepository,times(1)).findById(comment.getId());
        verify(commentRepository,times(1)).save(expected);
    }

    @Test
    public void updateComment_whenCommentIdRelatedCommentIsNotExistAndUpdateCommentRequestIsValid_shouldThrowNotFoundException(){

        Long commentId = DEFAULT_COMMENT_ID + 1;
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest("update-comment-request-text");

        assertThrows(NotFoundException.class,()-> commentService.updateComment(commentId,updateCommentRequest));

        verify(commentRepository,times(0)).save(any(Comment.class));
        verify(commentRepository,times(1)).findById(commentId);
    }

    @Test
    public void deleteComment_whenCommentIdRelatedCommentIsExist_deleteComment(){

        Long commentId = DEFAULT_COMMENT_ID;

        commentService.deleteComment(commentId);

        verify(commentRepository,times(1)).deleteById(commentId);
    }
}