package com.sda.store.service.implementation;

import com.sda.store.exception.ResourceAlreadyPresentInDatabase;
import com.sda.store.model.User;
import com.sda.store.repository.UserRepository;
import com.sda.store.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder;

    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public User create(User user) {
        User userInDatabase = userRepository.findByEmail(user.getEmail());
        if(userInDatabase != null){
            throw new ResourceAlreadyPresentInDatabase(String.format("User with email %s already exists",user.getEmail()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
