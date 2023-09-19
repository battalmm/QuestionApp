package com.korkmazyusufcan.questionapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private PostDto post;

    private UserDto user;

    private String text;

    private LocalDate creationDate;

}
