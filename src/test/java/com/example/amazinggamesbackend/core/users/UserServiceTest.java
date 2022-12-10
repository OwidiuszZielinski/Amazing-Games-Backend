package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserService userService;
    @BeforeEach
    public void setUp(){
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(passwordEncoder,userRepository);

    }


    @Test
    void should_return_UserDTo_fromRepository_byID(){
        //given
        final User givenUser = new User();
        givenUser.setId(1);
        givenUser.setUsername("example123");
        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(givenUser));
        //when
        final UserDTO userFromRepo = userService.getUserMapToDTO(givenUser.getId());
        //then
        assertNotNull(userFromRepo);
        verify(userRepository).findById(givenUser.getId());
        Assertions.assertEquals(givenUser.getUsername(),userFromRepo.getUsername());

    }

    @Test
    void should_return_all_users_fromRepository() {
        //given
        final User givenUserOne = new User();
        final User givenUserTwo = new User();
        givenUserOne.setId(1);
        givenUserTwo.setId(2);
        final int expectedListSize =2;
        when(userRepository.findAll()).thenReturn(List.of(givenUserOne,givenUserTwo));
        //when
        final List<UserDTO> allUsersFromRepository = userService.getAllUsers();
        //then
        assertNotNull(allUsersFromRepository);
        verify(userRepository).findAll();
        assertEquals(expectedListSize,allUsersFromRepository.size());
    }

    @Test
    void should_delete_userIds_fromRepository_params_OK() {
        //given
        final List<Integer> idToDelete = List.of(1);
        final User givenUserOne = new User();
        final User givenUserTwo = new User();
        givenUserOne.setId(1);
        givenUserTwo.setId(2);
        //when
        when(userRepository.findAllById(anyCollection())).thenReturn(List.of(givenUserOne,givenUserTwo));
        userService.deleteUsers(idToDelete);
        //then
        verify(userRepository).deleteAllByIdInBatch(idToDelete);


    }
    @Test
    void should_delete_userIds_fromRepository_params_empty() {
        //given
        final List<Integer> idToDelete = new ArrayList<>();

        //then
        Assertions.assertThrows(IllegalArgumentException.class,() ->
        {
            userService.deleteUsers(idToDelete);
        });


    }
    @Test
    void should_delete_userIds_fromRepository_bad_ids() {
        //given
        final List<Integer> idToDelete = List.of(3);
        //when
        //then
        Assertions.assertThrows(IllegalArgumentException.class,() ->
        {
            userService.deleteUsers(idToDelete);
        });


    }

    @Test
    void should_update_user_params_OK() {
        //given
        final int givenUserId = 1;
        final UserDTO toUpdateUserDTO = UserDTO.builder().username("afterUpdate").email("example@e.com").password("password").build();
        final User toUpdateUser = new User();
        final User beforeUpdate = new User();
        beforeUpdate.setUsername("beforeUpdate");
        beforeUpdate.setEmail("example@e.com");
        beforeUpdate.setPassword("password");
        toUpdateUser.fromDTO(toUpdateUserDTO);
        when(userRepository.findById(givenUserId)).thenReturn(Optional.of(beforeUpdate));
        when(userRepository.findAll()).thenReturn(List.of(beforeUpdate));
        //when
        final UserDTO updateUser = userService.updateUser(givenUserId ,toUpdateUserDTO);
        //then
        assertNotNull(updateUser);
        verify(userRepository).findById(givenUserId);
        Assertions.assertEquals(toUpdateUserDTO.getUsername(),updateUser.getUsername());
    }

    @Test
    void should_validate_username_params_OK() {
        //given
        final UserDTO givenUser = UserDTO.builder().username("minimum6characters").build();
        final User user = new User();
        user.setUsername("userInRepository");
        user.setEmail("minimum@gmail.com");
        user.setPassword("password");
        when(userRepository.findAll()).thenReturn(List.of(user));
        //when
        boolean validate = userService.validateUsername(givenUser);
        //then
        assertFalse(validate);
    }

    @Test
    void should_validate_username_to_short() {
        //given
        final UserDTO givenUser = UserDTO.builder().username("short").build();
        final User user = new User();
        user.setUsername("minimum6characters");
        user.setEmail("minimum@gmail.com");
        user.setPassword("password");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        //when
        boolean validate = userService.validateUsername(givenUser);
        //then
        assertTrue(validate);
    }

    @Test
    void should_validate_email_params_OK() {
        //given
        final UserDTO givenUser = UserDTO.builder().username("example").email("example@gmail.com").build();
        final User user = new User();
        user.setUsername("example");
        user.setEmail("exists@gmail.com");
        user.setPassword("password");
        when(userRepository.findAll()).thenReturn(List.of(user));
        //when
        boolean validate = userService.validateEmail(givenUser);
        //then
        assertFalse(validate);
    }

    @Test
    void should_validate_email_bad_email() {
        //given
        final UserDTO givenUser = UserDTO.builder().username("example").email("bad@").build();
        final User user = new User();
        user.setUsername("minimum6characters");
        user.setEmail("minimum@gmail.com");
        user.setPassword("password");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        //when
        boolean validate = userService.validateEmail(givenUser);
        //then
        assertTrue(validate);
    }

    @Test
    void should_return_user_byId() {
        //given
        final User givenUser = new User();
        givenUser.setId(1);
        givenUser.setUsername("example123");
        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(givenUser));
        //when
        final User userFromRepo = userService.userById(givenUser.getId());
        //then
        assertNotNull(userFromRepo);
        verify(userRepository).findById(givenUser.getId());
        Assertions.assertEquals(givenUser.getUsername(),userFromRepo.getUsername());


    }
}