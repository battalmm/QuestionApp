package com.korkmazyusufcan.questionapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentRequest {

    private String text;
    private Long postId;
    private Long userId;

}
