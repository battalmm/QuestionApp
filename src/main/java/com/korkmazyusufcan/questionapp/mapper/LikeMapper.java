package com.korkmazyusufcan.questionapp.mapper;

import com.korkmazyusufcan.questionapp.dto.LikeDto;
import com.korkmazyusufcan.questionapp.dto.response.LikeResponse;
import com.korkmazyusufcan.questionapp.entity.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    private final PostMapper postMapper;
    private final UserMapper userMapper;

    public LikeMapper(PostMapper postMapper, UserMapper userMapper) {
        this.postMapper = postMapper;
        this.userMapper = userMapper;
    }

    public LikeDto toDto(Like like){
        return new LikeDto(
                postMapper.toDto(like.getPost()),
                userMapper.toDto(like.getUser()),
                like.getCreationDate()
        );
    }

    public LikeResponse toLikeRespone(Like like){
        return new LikeResponse(
                like.getId(),
                like.getPost().getId(),
                like.getUser().getId()
        );
    }
}
