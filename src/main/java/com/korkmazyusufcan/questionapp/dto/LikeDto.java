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
public class LikeDto {

    private Long id;

    private PostDto post;

    private UserDto user;

    private LocalDate creationDate;

}
