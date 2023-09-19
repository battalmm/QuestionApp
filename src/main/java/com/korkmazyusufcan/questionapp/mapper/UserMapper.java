package com.korkmazyusufcan.questionapp.mapper;

import com.korkmazyusufcan.questionapp.dto.UserDto;
import com.korkmazyusufcan.questionapp.entity.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(User user){
        return new UserDto(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getCreationDate()
        );
    }

    public User toEntity(UserDto userDto){
        return new User(
                userDto.getName(),
                userDto.getSurname(),
                userDto.getEmail()
        );
    }

    public List<UserDto> toDtoList(List<User> userList){
        return userList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<User> toEntityList(List<UserDto> userDtoList){
        return userDtoList
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}
