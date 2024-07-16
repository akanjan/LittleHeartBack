package com.ecom.controllers;

import com.ecom.exceptions.ApiException;
import com.ecom.payloads.JwtAuthRequest;
import com.ecom.payloads.JwtAuthResponse;
import com.ecom.payloads.UserDto;
import com.ecom.security.JwtTokenHelper;
import com.ecom.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
    ) throws Exception {
        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        try {
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e)
        {
            System.out.println("Invalid Details !!");
            throw new ApiException("Invalid Username Or Password !!");
        }
    }


    //Register New User Api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
    {
        UserDto registerNewUser = this.userService.registerNewUser(userDto);
        registerNewUser.setUserPic("default.png");
        return new ResponseEntity<>(registerNewUser,HttpStatus.CREATED);
    }


    //Return details of logged in current user
    @GetMapping("/current-user")
    public UserDto getCurrentUser(Principal principal)
    {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(principal.getName());
        UserDto userDto = this.modelMapper.map(userDetails, UserDto.class);
        return userDto;
    }

}
