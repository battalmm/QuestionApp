package com.korkmazyusufcan.questionapp.repository;

import com.korkmazyusufcan.questionapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}