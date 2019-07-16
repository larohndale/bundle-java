/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.user;

import com.akveo.bundlejava.authentication.SignUpDTO;
import com.akveo.bundlejava.authentication.exception.PasswordsDontMatchException;
import com.akveo.bundlejava.authentication.exception.UserNotFoundHttpException;
import com.akveo.bundlejava.role.RoleService;
import com.akveo.bundlejava.settings.Settings;
import com.akveo.bundlejava.settings.SettingsService;
import com.akveo.bundlejava.user.exception.UserAlreadyExistsException;
import com.akveo.bundlejava.user.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    private RoleService roleService;

    private SettingsService settingsService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ModelMapper modelMapper,
                       SettingsService settingsService,
                       RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.settingsService = settingsService;
        this.roleService = roleService;
    }

    public User findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
    }

    @Transactional
    public User register(SignUpDTO signUpDTO) throws UserAlreadyExistsException {
        if (!signUpDTO.getPassword().equals(signUpDTO.getConfirmPassword())) {
            throw new PasswordsDontMatchException();
        }

        String email = signUpDTO.getEmail();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(email);
        }

        User user = signUpUser(signUpDTO);

        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = changePasswordRequest.getUser();

        String encodedPassword = encodePassword(changePasswordRequest.getPassword());
        user.setPasswordHash(encodedPassword);

        userRepository.save(user);
    }

    public UserDTO getUserById(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND)
        );

        return modelMapper.map(existingUser, UserDTO.class);
    }

    @Transactional
    public UserDTO updateUserById(Long userId, UserDTO userDTO) {
        return updateUser(userId, userDTO);
    }

    @Transactional
    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public UserDTO getCurrentUser() {
        User user = UserContextHolder.getUser();
        user.setSettings(settingsService.getSettingsByUserId(user.getId()));

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO updateCurrentUser(UserDTO userDTO) {
        User user = UserContextHolder.getUser();
        Long id = user.getId();

        return updateUser(id, userDTO);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        // In current version password and role are default
        user.setPasswordHash(encodePassword("testPass"));
        user.setRoles(new HashSet<>(Collections.singletonList(roleService.getDefaultRole())));
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    private UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundHttpException(
                        "User with id: " + id + " not found", HttpStatus.NOT_FOUND)
                );

        User updatedUser = modelMapper.map(userDTO, User.class);
        updatedUser.setId(id);
        updatedUser.setPasswordHash(existingUser.getPasswordHash());
        // Current version doesn't update roles
        updatedUser.setRoles(existingUser.getRoles());

        userRepository.save(updatedUser);

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    private User signUpUser(SignUpDTO signUpDTO) {
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setUserName(signUpDTO.getFullName());

        String encodedPassword = encodePassword(signUpDTO.getPassword());
        user.setPasswordHash(encodedPassword);
        user.setRoles(new HashSet<>(Collections.singletonList(roleService.getDefaultRole())));
        user.setSettings(new Settings("cosmic"));
        return user;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
