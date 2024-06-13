package com.storeeverythin.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.storeeverythin.model.UserEntity;
import com.storeeverythin.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String register(RegistrationRequest request) {
        Optional<UserEntity> userOptional = userRepository.findUserByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            return "Username is already taken";
        }

        UserEntity newUser = new UserEntity();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setUsername(request.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setAge(request.getAge());
        newUser.setRoles(List.of("admin"));

        userRepository.save(newUser);

        return "Registration successful for user: " + request.getUsername();
    }

    public String confirmToken(String token) {
        // Logika potwierdzenia tokenu
        return "Token confirmed successfully!";
    }
}
