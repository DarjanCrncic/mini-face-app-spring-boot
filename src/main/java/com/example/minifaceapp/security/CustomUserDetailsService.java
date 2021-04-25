package com.example.minifaceapp.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.repositories.FaceUserRepository;
 
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private FaceUserRepository faceUserRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FaceUser faceUser = faceUserRepository.findByUsername(username);
        if (faceUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(faceUser);
    }
 
}


