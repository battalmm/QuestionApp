package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.UserDto;
import com.korkmazyusufcan.questionapp.dto.request.UserCreateRequest;
import com.korkmazyusufcan.questionapp.dto.request.UserUpdateRequest;
import com.korkmazyusufcan.questionapp.entity.User;
import com.korkmazyusufcan.questionapp.mapper.UserMapper;
import com.korkmazyusufcan.questionapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private  final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto createNewUser(UserCreateRequest userCreateRequest) {
        //TODO
        // THIS MAIL ADDRESS IS ALREADY TAKEN CHANGE EXCEPTION
        // check mails
        User newUser = new User(
                userCreateRequest.getName(),
                userCreateRequest.getSurname(),
                userCreateRequest.getEmail()
        );

        userRepository.save(newUser);
        return userMapper.toDto(newUser);

    }

    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        //TODO
        // USER NOT FOUND EXCEPTION
        User user = userRepository.findById(id).orElseThrow(null);
        return userMapper.toDto(user);
    }

    public void deleteUserById(Long userId) {
        //TODO
        // USER NOT FOUND EXCEPTION
        //User user = userRepository.findById(id).orElseThrow(null);
        userRepository.deleteById(userId);

    }

    public UserDto updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        //TODO
        // USER NOT FOUND EXCEPTION
        User user = userRepository.findById(userId).orElseThrow(null);
        user.setEmail(userUpdateRequest.getEmail());
        user.setName(userUpdateRequest.getName());
        user.setSurname(userUpdateRequest.getSurname());
        userRepository.save(user);
        return userMapper.toDto(user);


    }
}
