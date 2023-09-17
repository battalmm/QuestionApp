package com.korkmazyusufcan.questionapp.dto;

import com.korkmazyusufcan.questionapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    private UserDto user;

    private String text;

    private LocalDate creationDate;

}
