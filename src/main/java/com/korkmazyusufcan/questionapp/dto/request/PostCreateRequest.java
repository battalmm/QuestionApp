package com.korkmazyusufcan.questionapp.dto.request;

import com.korkmazyusufcan.questionapp.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    private Long userId;

    private String text;

}
