package com.korkmazyusufcan.questionapp.dto.response;

import com.korkmazyusufcan.questionapp.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private String userName;
    private String text;

    public CommentResponse(Comment comment){
        this.id = comment.getId();
        this.userName = comment.getUser().getName();
        this.text = comment.getText();
    }

}
