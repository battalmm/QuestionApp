package com.korkmazyusufcan.questionapp.controller;

import com.korkmazyusufcan.questionapp.service.LikeService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    private  final LikeService likeService;

    public LikeController(LikeService likeService) {

        this.likeService = likeService;
    }
}
