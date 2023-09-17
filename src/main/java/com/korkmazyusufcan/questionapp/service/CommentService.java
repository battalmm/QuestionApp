package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.CommentDto;
import com.korkmazyusufcan.questionapp.dto.LikeDto;
import com.korkmazyusufcan.questionapp.entity.Comment;
import com.korkmazyusufcan.questionapp.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {

        this.commentRepository = commentRepository;
    }

}
