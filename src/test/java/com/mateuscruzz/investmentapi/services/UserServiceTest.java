package com.mateuscruzz.investmentapi.services;

import com.mateuscruzz.investmentapi.controllers.CreateUserDTO;
import com.mateuscruzz.investmentapi.model.User;
import com.mateuscruzz.investmentapi.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser{

        @Test
        @DisplayName("Should create a user")
        void shouldCreateUser(){

            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "123");

            // Act
            var output = userService.createUser(input);

            //Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(),userCaptured.getUsername());
            assertEquals(input.email(),userCaptured.getEmail());
            assertEquals(input.password(),userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception on errors")
        void shouldThrowExceptionOnErrors(){
            // Arrange

            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "123");

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should get user by id when optional is present")
        void shouldGetUserByIdWhenOptionalIsPresent() {

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(user.getUserId().toString());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(),uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id when optional is empty")
        void shouldGetUserByIdWhenOptionalIsEmpty() {

            //Arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(userId.toString());

            //Assert
            assertTrue(output.isEmpty());
            assertEquals(userId,uuidArgumentCaptor.getValue());
        }


    }

    @Nested
    class getAllUsers {

        @Test
        @DisplayName("Should get user all users")
        void shouldGetAllUsers() {

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(userList).
                    when(userRepository).
                    findAll();

            //Act
            var output = userService.getAllUsers();

            //Assert
            assertNotNull(output);
            assertEquals(userList.size(),output.size());
        }
    }
}