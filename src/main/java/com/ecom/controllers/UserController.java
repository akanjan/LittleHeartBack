package com.ecom.controllers;

import com.ecom.payloads.ApiResponse;
import com.ecom.payloads.UserDto;
import com.ecom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    //Post --Create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUserDto = this.userService.createUser(userDto);
        return  new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }


    //Put --Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid)
    {
        UserDto updateUser = this.userService.updateUser(userDto, uid);
        return ResponseEntity.ok(updateUser);
    }


    //Delete --Delete user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
    {
        this.userService.deleteUser(uid);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
    }


    //Get -- Get All user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser()
    {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    //Get --Get Single User
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId)
    {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/totalusers")
    public Long totalUsers(){
        Long users = this.userService.totalUsers();
        return users;
    }
}
