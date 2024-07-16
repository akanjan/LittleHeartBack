package com.ecom.controllers;

import com.ecom.entities.User;
import com.ecom.exceptions.ResourceNotFoundException;
import com.ecom.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/all/")
@CrossOrigin("*")
public class AllUsersAccessController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PutMapping("/{email}/password/{password}")
    public String changePassword(@PathVariable String email, @PathVariable String password)
    {
        User user = this.userRepo.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email", "email id", 0));
        user.setPassword(this.passwordEncoder.encode(password));
        this.userRepo.save(user);
        return user.getPassword();
    }
}
