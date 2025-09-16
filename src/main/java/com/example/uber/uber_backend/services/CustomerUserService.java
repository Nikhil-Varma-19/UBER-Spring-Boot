package com.example.uber.uber_backend.services;

import com.example.uber.uber_backend.entities.User;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.repostities.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByEmail(username);
        if(user.isEmpty()) throw new UsernameNotFoundException("");
        return user.get();
    }

    public User getUserById(Long id){
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()) throw new ResourceNotFound("User Not Found");
        return user.get();
    }
}
