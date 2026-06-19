package com.SchoolManagementSystem.System.security.service;


import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.security.AuthUserRepository;
import com.SchoolManagementSystem.System.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        AuthUser user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserPrincipal(user);
    }

}

