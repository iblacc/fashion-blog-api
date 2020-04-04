package com.laylasahara.fashionblogapi.services;

import com.laylasahara.fashionblogapi.exceptions.EntityAlreadyExistException;
import com.laylasahara.fashionblogapi.models.User;
import com.laylasahara.fashionblogapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User newUser) {
        User user = userRepository.findFirstByEmail(newUser.getEmail());

        if(user != null) {
            throw new EntityAlreadyExistException("User already exist");
        }
        userRepository.save(newUser);
    }
}
