package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {

        this.likeRepository = likeRepository;
    }
}
