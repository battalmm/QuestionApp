package com.korkmazyusufcan.questionapp.mapper;

import com.korkmazyusufcan.questionapp.dto.CommentDto;
import com.korkmazyusufcan.questionapp.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final PostMapper postMapper;
    private final UserMapper userMapper;

    public CommentMapper(PostMapper postMapper,
                         UserMapper userMapper) {
        this.postMapper = postMapper;
        this.userMapper = userMapper;

    }

    public CommentDto toDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                postMapper.toDto(comment.getPost()),
                userMapper.toDto(comment.getUser()),
                comment.getText(),
                comment.getCreationDate()
        );
    }
}
