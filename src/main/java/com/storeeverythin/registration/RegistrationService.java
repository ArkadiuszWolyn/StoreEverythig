package com.storeeverythin.registration;

import com.storeeverythin.model.UserEntity;
import com.storeeverythin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setAge(request.getAge());
        newUser.setRoles(Collections.singletonList("LIMITED_USER"));

        userRepository.save(newUser);

        return "Registration successful for user: " + request.getUsername();
    }
}
