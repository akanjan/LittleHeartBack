package com.ecom.security;

import com.ecom.entities.User;
import com.ecom.exceptions.ResourceNotFoundException;
import com.ecom.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Loading User From database by UserName
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "Email : " + username, 0));
        return user;
    }

}
