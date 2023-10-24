package com.korkmazyusufcan.questionapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korkmazyusufcan.questionapp.dto.request.PostCommentRequest;
import com.korkmazyusufcan.questionapp.dto.response.CommentResponse;
import com.korkmazyusufcan.questionapp.entity.Comment;
import com.korkmazyusufcan.questionapp.exception.NotFoundException;
import com.korkmazyusufcan.questionapp.service.CommentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import supports.TestSupport;

import java.util.List;


@DirtiesContext
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server.port=0")
class CommentControllerTest extends TestSupport {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Test
    public void getAllCommentsTest_ShouldReturnCommentResponseList() throws Exception{
        Comment comment = generateComment();
        List<CommentResponse> commentResponseList = generateCommentResponseList(comment);

        Mockito.when(commentService.getAllComments()).thenReturn(commentResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(COMMENT_BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(comment.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName", Matchers.is(comment.getUser().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text", Matchers.is(comment.getText())));
    }

    @Test
    public void getAllCommentsByUserIdTest_whenUserIdParameterIsValid_shouldReturnListOfCommentResponse() throws Exception {
        String uri = COMMENT_BASE_URL+"/user";
        Long userIdParameter = DEFAULT_USER_ID;
        Comment comment = generateComment();
        List<CommentResponse> commentResponseList = generateCommentResponseList(comment);

        Mockito.when(commentService.getAllCommentsByUserId(userIdParameter)).thenReturn(commentResponseList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId",userIdParameter.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(comment.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName", Matchers.is(comment.getUser().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text", Matchers.is(comment.getText())));
    }

    @Test
    public void getAllCommentsByUserIdTest_whenUserIdParameterIsNotValid_shouldReturnHTTP400BadRequest() throws Exception {
        String uri = COMMENT_BASE_URL+"/user";
        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId",""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getAllCommentsByPostIdTest_whenPostIdParameterIsValid_shouldReturnListOfCommentResponse() throws  Exception {
        String uri = COMMENT_BASE_URL+"/post";
        Long postIdRequest = DEFAULT_POST_ID;
        Comment comment = generateComment();
        List<CommentResponse> commentResponseList = generateCommentResponseList(comment);

        Mockito.when(commentService.getAllCommentsByPostId(postIdRequest)).thenReturn(commentResponseList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("postId", postIdRequest.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(commentResponseList.get(0).getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName", Matchers.is(commentResponseList.get(0).getUserName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text", Matchers.is(commentResponseList.get(0).getText())));
    }

    @Test
    public void getAllCommentsByPostIdTest_whenPostIdParameterIsNotValid_shouldThrowHTTP400BadRequest() throws  Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(COMMENT_BASE_URL+"/post")
                        .param("userId","")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getCommentByIdTest_whenCommentIdIsValid_shouldReturnSingleCommentResponse() throws Exception {
        Long commentId = DEFAULT_COMMENT_ID;
        Comment comment = generateComment();
        CommentResponse commentResponse = generateCommentResponse(comment);

        Mockito.when(commentService.getCommentById(commentId)).thenReturn(commentResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(COMMENT_BASE_URL+"/"+commentId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(commentResponse.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.is(commentResponse.getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", Matchers.is(commentResponse.getUserName())));
    }

    @Test
    public void getCommentByIdTest_whenCommentIdIsNotValid_shouldThrowHTTP404NotFound() throws  Exception {

        Mockito.when(commentService.getCommentById(1L)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(COMMENT_BASE_URL+"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "battal",roles = "USER")
    public void createCommentTest_whenPostCommentRequestIsValidAndUserAuthenticated_shouldReturnCommentResponse() throws Exception {
        PostCommentRequest postCommentRequest = generatePostCommentRequest();
        String jsonValueOfRequest = objectMapper.writeValueAsString(postCommentRequest);
        CommentResponse commentResponse = CommentResponse
                .builder()
                .text(postCommentRequest.getText())
                .build();

        Mockito.when(commentService.createComment(postCommentRequest)).thenReturn(commentResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(COMMENT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValueOfRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createCommentTest_whenUserNotAuthenticated_shouldThrowHTTP401Unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(COMMENT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    //TODO
    // Add Validation And Check Again

   /* @Test
    @WithMockUser(username = "battal",roles = "USER")
    public void createCommentTest_wheUserAuthenticatedButPostCommentRequestIsNotValid_shouldThrow() throws Exception {
        PostCommentRequest postCommentRequest = generatePostCommentRequest();
        postCommentRequest.setUserId(-1L);
        postCommentRequest.setPostId(-1L);
        String jsonValueOfRequest = objectMapper.writeValueAsString(postCommentRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(COMMENT_BASE_URL)
                        .content(jsonValueOfRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    */
}
    //TODO
    // updateComment
    // deleteComment
