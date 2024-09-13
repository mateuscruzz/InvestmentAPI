package com.mateuscruzz.investmentapi.services;

import com.mateuscruzz.investmentapi.controllers.DTO.CreateUserDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.UpdateUserDTO;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Nested
    class deleteById {
        @Test
        @DisplayName("Should delete user if user exists")
        void shouldDeleteUserIfUserExists() {

            //Arrange

            doReturn(true).
                    when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            doNothing().
                    when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            //Act
            userService.deleteUserById(userId.toString());

            //Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should not delete user if user dont exists")
        void shouldNotDeleteUserIfUserDontExists() {

            //Arrange
            doReturn(false).
                    when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();

            //Act
            userService.deleteUserById(userId.toString());

            //Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1))
                    .existsById(uuidArgumentCaptor.getValue());

            verify(userRepository, times(0)).deleteById(any());
        }
    }

    @Nested
    class updateUserById {

        @Test
        @DisplayName("Should update user by id when user exists and username and password is filled")
        void shouldUpdateUserByIdWhenUserExistsAndUserNameAndPasswordIsFilled() {
            //Arrange
            var updateUserDTO = new UpdateUserDTO(
                    "newUsername",
                    "newPassword"
            );

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            //Act
           userService.updateUserById(user.getUserId().toString(),
            updateUserDTO);

            //Assert
            assertEquals(user.getUserId(),uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateUserDTO.username(),userCaptured.getUsername());
            assertEquals(updateUserDTO.password(),userCaptured.getPassword());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1))
                    .save(user);
        }

        @Test
        @DisplayName("Should not updateUserById if user dont exists")
        void shouldNotUpdateUserByIdIfUserDontExists() {
            //Arrange
            var updateUserDTO = new UpdateUserDTO(
                    "newUsername",
                    "newPassword"
            );

            var userId = UUID.randomUUID();

            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            userService.updateUserById(userId.toString(),updateUserDTO);

            //Assert
            assertEquals(userId,uuidArgumentCaptor.getValue());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0))
                    .save(any());
        }
    }
}