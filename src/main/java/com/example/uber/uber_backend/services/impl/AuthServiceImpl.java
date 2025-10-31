package com.example.uber.uber_backend.services.impl;

import com.example.uber.uber_backend.constants.Role;
import com.example.uber.uber_backend.dtos.*;
import com.example.uber.uber_backend.entities.Driver;
import com.example.uber.uber_backend.entities.Rider;
import com.example.uber.uber_backend.entities.User;
import com.example.uber.uber_backend.exceptions.FileException;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.exceptions.RuntimeConflictException;
import com.example.uber.uber_backend.filters.JWTFilter;
import com.example.uber.uber_backend.repostities.DriverRepository;
import com.example.uber.uber_backend.repostities.UserRepository;
import com.example.uber.uber_backend.services.*;
import com.example.uber.uber_backend.utilis.EmailService;
import com.example.uber.uber_backend.utilis.OTPGenerate;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
    private final OTPService otpService;
    private final EmailService emailService;
    private final DriverRepository driverRepository;

    @Value("${dir.uploadFile}")
    private String uploadDir;

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
    public DriverDto newDriver(Long userId, String vehicleId, MultipartFile file) {
        double longitude = 77.1025;
        double latitude = 28.7041;
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new ResourceNotFound("User Not Found");
        if (user.get().getRoles().contains(Role.DRIVER))
            throw new RuntimeConflictException("User is already a driver.");
        Driver newDriverCreate = Driver.builder()
                .isAvailable(true).user(user.get()).vehicleId(vehicleId).rating(0.0).currentLocation(location)
                .build();
        try{
            File folderPath=new File(uploadDir);
            if(!folderPath.exists()) folderPath.mkdirs();
            String fileName= UUID.randomUUID()+"_"+file.getOriginalFilename();
            String uploadPath=uploadDir+"/"+fileName;
            Path filePath= Paths.get(uploadPath);
            Files.write(filePath,file.getBytes());
            newDriverCreate.setFileName(file.getOriginalFilename());
            newDriverCreate.setUpdateFileName(fileName);
            newDriverCreate.setFilePath(uploadPath);
        } catch (Exception e) {
            throw new FileException("Cannot upload the file : "+file.getOriginalFilename());
        }
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

    @Override
    public void forgotPassword(String email) {
        Optional<User> isEmail=userRepository.findByEmail(email);
        if(isEmail.isEmpty()) throw  new ResourceNotFound("Email not Found");
        String otp= OTPGenerate.generateOtp(6);
        otpService.createForgotPassword(otp,isEmail.get().getId());
        // Should be store in the DB
        String text="OTP to change the password : <otp>";
        text=text.replaceAll("<otp>",otp);
        System.out.println(text);
        String to[]={"nikhilkumarvarma19@gmail.com"};
       emailService.sendEmail(to,"OTP for password change",text,null);
    }
}
