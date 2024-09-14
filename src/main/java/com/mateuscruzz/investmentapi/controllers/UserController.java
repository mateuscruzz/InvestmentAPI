package com.mateuscruzz.investmentapi.controllers;

import com.mateuscruzz.investmentapi.controllers.DTO.AccountResponseDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.CreateAccountDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.CreateUserDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.UpdateUserDTO;
import com.mateuscruzz.investmentapi.model.Account;
import com.mateuscruzz.investmentapi.model.User;
import com.mateuscruzz.investmentapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        var userId = userService.createUser(createUserDTO);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString() )).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return ResponseEntity.ok(list);
    }

    @PutMapping("{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateUserById(userId, updateUserDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId,
                                               @RequestBody CreateAccountDTO createAccountDTO){
        userService.createAccount(userId, createAccountDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> listUserAccounts(@PathVariable("userId") String userId){
        var accounts = userService.listAccounts(userId);

        return ResponseEntity.ok(accounts);
    }
}
