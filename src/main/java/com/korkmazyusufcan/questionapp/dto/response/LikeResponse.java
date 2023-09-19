package com.korkmazyusufcan.questionapp.dto.response;

import com.korkmazyusufcan.questionapp.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {

    private Long id;
    private Long postId;
    private Long userId;

    public LikeResponse(Like like) {
        this.id = like.getId();
        this.postId = like.getPost().getId();
        this.userId = like.getUser().getId();
    }

}
