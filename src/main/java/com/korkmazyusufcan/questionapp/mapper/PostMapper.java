package com.korkmazyusufcan.questionapp.mapper;

import com.korkmazyusufcan.questionapp.dto.PostDto;
import com.korkmazyusufcan.questionapp.entity.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    private final UserMapper userMapper;

    public PostMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PostDto toDto(Post post){
        return new PostDto(
                post.getId(),
                userMapper.toDto(post.getUser()),
                post.getText(),
                post.getCreationDate()
        );
    }

    public List<PostDto> toDtoList(List<Post> postList){
        return postList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
