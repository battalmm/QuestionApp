package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.dto.UserDto;
import com.korkmazyusufcan.questionapp.dto.request.UserCreateRequest;
import com.korkmazyusufcan.questionapp.dto.request.UserUpdateRequest;
import com.korkmazyusufcan.questionapp.entity.User;
import com.korkmazyusufcan.questionapp.exception.AlreadyExistException;
import com.korkmazyusufcan.questionapp.exception.ExceptionEntity;
import com.korkmazyusufcan.questionapp.exception.NotFoundException;
import com.korkmazyusufcan.questionapp.mapper.UserMapper;
import com.korkmazyusufcan.questionapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private  final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto createNewUser(UserCreateRequest userCreateRequest) {
        //TODO
        // ADD AUTH
        if(userRepository.findByEmail(userCreateRequest.getEmail()) == null)
        {
            User newUser = new User(
                    userCreateRequest.getName(),
                    userCreateRequest.getSurname(),
                    userCreateRequest.getEmail(),
                    userCreateRequest.getPassword()
            );
            userRepository.save(newUser);
            return userMapper.toDto(newUser);
        }
        else
        {
            throw new AlreadyExistException("This mail is already taken" ,ExceptionEntity.User);
        }
    }

    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException(ExceptionEntity.User));
        return userMapper.toDto(user);
    }

    public UserDto updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException(ExceptionEntity.User));
        User mailCheckedUser = userRepository.findByEmail(userUpdateRequest.getEmail());
        if(mailCheckedUser == null || Objects.equals(user.getId(), mailCheckedUser.getId()))
        {
            user.setEmail(userUpdateRequest.getEmail());
            user.setName(userUpdateRequest.getName());
            user.setSurname(userUpdateRequest.getSurname());
            userRepository.save(user);
            return userMapper.toDto(user);
        }
        else
        {
            throw new AlreadyExistException("This mail is already taken" , ExceptionEntity.User);
        }
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    protected User findUserById(Long id){
        return userRepository.findById(id).orElseThrow( () -> new NotFoundException(ExceptionEntity.User));
    }

    protected Boolean isEmailExist(String email){
        return userRepository.findByEmail(email) != null;
    }
}
