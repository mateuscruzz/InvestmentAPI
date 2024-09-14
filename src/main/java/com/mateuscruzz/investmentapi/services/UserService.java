package com.mateuscruzz.investmentapi.services;

import com.mateuscruzz.investmentapi.controllers.DTO.AccountResponseDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.CreateAccountDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.CreateUserDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.UpdateUserDTO;
import com.mateuscruzz.investmentapi.model.Account;
import com.mateuscruzz.investmentapi.model.BillingAdress;
import com.mateuscruzz.investmentapi.model.User;
import com.mateuscruzz.investmentapi.repositories.AccountRepository;
import com.mateuscruzz.investmentapi.repositories.BiilingAdressRepository;
import com.mateuscruzz.investmentapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BiilingAdressRepository billingAdressRepository;

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
    }

    public void deleteUserById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDTO createAccountDTO){

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
                UUID.randomUUID(),
                user,
                null,
                createAccountDTO.description(),
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAdress = new BillingAdress(
            accountCreated.getAccountId(),
                account,
                createAccountDTO.street(),
                createAccountDTO.number()
        );

        billingAdressRepository.save(billingAdress);

    }

    public List<AccountResponseDTO> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDTO(ac.getAccountId().toString(),
                        ac.getDescription()))
                .toList();
    }
}
