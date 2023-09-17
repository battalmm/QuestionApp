package com.korkmazyusufcan.questionapp.controller;

import com.korkmazyusufcan.questionapp.dto.UserDto;
import com.korkmazyusufcan.questionapp.dto.request.UserCreateRequest;
import com.korkmazyusufcan.questionapp.dto.request.UserUpdateRequest;
import com.korkmazyusufcan.questionapp.entity.User;
import com.korkmazyusufcan.questionapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService){

        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createNewUser(@RequestBody UserCreateRequest userCreateRequest){
        return ResponseEntity.ok(userService.createNewUser(userCreateRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){


        userService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,
                                           @RequestBody UserUpdateRequest userUpdateRequest){
       return ResponseEntity.ok(userService.updateUser(userId,userUpdateRequest));
    }

}
