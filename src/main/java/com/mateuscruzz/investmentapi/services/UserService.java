package com.mateuscruzz.investmentapi.services;

import com.mateuscruzz.investmentapi.controllers.CreateUserDTO;
import com.mateuscruzz.investmentapi.controllers.UpdateUserDTO;
import com.mateuscruzz.investmentapi.model.User;
import com.mateuscruzz.investmentapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UUID createUser(CreateUserDTO createUserDTO) {

        var entity = new User(
                UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                Instant.now(),
                null
        );

        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDTO updateUserDTO) {

        var id = UUID.fromString(userId);
        var userEntity =  userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDTO.username() != null) {
                user.setUsername(updateUserDTO.username());
            }

            if (updateUserDTO.password() != null) {
                user.setPassword(updateUserDTO.password());
            }

            userRepository.save(user);
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUserById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }
}
