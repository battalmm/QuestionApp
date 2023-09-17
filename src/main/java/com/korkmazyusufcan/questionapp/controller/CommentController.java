package com.korkmazyusufcan.questionapp.controller;

import com.korkmazyusufcan.questionapp.service.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }
}
