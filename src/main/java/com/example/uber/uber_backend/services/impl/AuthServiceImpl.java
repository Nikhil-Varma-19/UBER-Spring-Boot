package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.constants.Role;
import com.example.uber.uber_backend.dtos.DriverDto;
import com.example.uber.uber_backend.dtos.LoginResponseDto;
import com.example.uber.uber_backend.dtos.SignUpDto;
import com.example.uber.uber_backend.dtos.UserDto;
import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Rider;
import com.example.uber.uber_backend.entities.User;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.exceptions.RuntimeConflictException;
import com.example.uber.uber_backend.filters.JWTFilter;
import com.example.uber.uber_backend.repostities.UserRepository;
import com.example.uber.uber_backend.services.AuthService;
import com.example.uber.uber_backend.services.DriverService;
import com.example.uber.uber_backend.services.RiderService;
import com.example.uber.uber_backend.services.WalletService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTFilter jwtFilter;

    @Override
    public LoginResponseDto login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authentication.getPrincipal();
        String token = jwtFilter.generateJWTToken(user);
        String refreshToken = jwtFilter.generateRefreshToken(user);
        return LoginResponseDto.builder()
                .role(user.getRoles())
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public UserDto signup(SignUpDto signUpDto) {
        User isEmail = userRepository.findByEmail(signUpDto.getEmail()).orElse(null);
        if (isEmail != null) {
            throw new RuntimeConflictException("Enter the new email");
        }
        User user = modelMapper.map(signUpDto, User.class);
        user.setRoles(Set.of(Role.DRIVER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(user);
        Rider newRiderRating = riderService.createNewRider(userSaved);
        if (newRiderRating == null) {
            throw new RuntimeConflictException("Error while creating the User.");
        }
        walletService.createNewWallet(userSaved);
        return modelMapper.map(userSaved, UserDto.class);
    }

    @Override
    public DriverDto newDriver(Long userId, String vehicleId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new ResourceNotFound("User Not Found");
        if (!user.get().getRoles().contains(Role.DRIVER))
            throw new RuntimeConflictException("User is already a driver.");
        Driver newDriverCreate = Driver.builder()
                .isAvailable(true).user(user.get()).vehicleId(vehicleId).rating(0.0)
                .build();
        Driver driverSaved = driverService.createNewDriver(newDriverCreate);
        if (driverSaved.getId() == null) throw new RuntimeConflictException("Driver not saved");
        user.get().getRoles().add(Role.DRIVER);
        userRepository.save(user.get());
        return modelMapper.map(driverSaved, DriverDto.class);
    }

    @Override
    public String refreshToken(Cookie[] cookies) {
        String key = "refreshToken";
        String tokenFind = null;
        if (cookies == null || cookies.length < 1)
            throw new RuntimeConflictException("Cannot access the refresh token");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                tokenFind = cookie.getValue();
                break;
            }
        }
        if (tokenFind == null) throw new RuntimeConflictException("Cannot access the refresh token");
        Long userId = jwtFilter.getIdFromRefreshToken(tokenFind);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new ResourceNotFound("User Not Found");
        return jwtFilter.generateJWTToken(user.get());
    }
}
