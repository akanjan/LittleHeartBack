package com.ecom.services.impl;

import com.ecom.config.AppConstants;
import com.ecom.entities.Role;
import com.ecom.entities.User;
import com.ecom.exceptions.ResourceNotFoundException;
import com.ecom.payloads.UserDto;
import com.ecom.repositories.RoleRepo;
import com.ecom.repositories.UserRepo;
import com.ecom.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;


    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findById(AppConstants.CUSTOMER_USER).get();
        user.getRoles().add(role);
        user.setUserPic("default.png");
        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("user", "Id", userId));

        //System.out.println("Your Password is"+ userDto.getPassword());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNo(userDto.getPhoneNo());
        user.setUserPic(userDto.getUserPic());
        user.setAddress(userDto.getAddress());
        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);

    }

    public Long totalUsers(){
        Long users = this.userRepo.countUsers();
        return users;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "Email : " + email, 0));
        return this.userToDto(user);
    }


    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
