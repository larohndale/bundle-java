/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.user;

import com.akveo.bundlejava.authentication.SignUpDTO;
import com.akveo.bundlejava.authentication.TokenValidationService;
import com.akveo.bundlejava.authentication.exception.PasswordsDontMatchException;
import com.akveo.bundlejava.authentication.exception.UserNotFoundHttpException;
import com.akveo.bundlejava.image.Image;
import com.akveo.bundlejava.image.ImageRepository;
import com.akveo.bundlejava.role.RoleService;
import com.akveo.bundlejava.settings.Settings;
import com.akveo.bundlejava.settings.SettingsService;
import com.akveo.bundlejava.user.exception.AccessTokenNotFoundHttpException;
import com.akveo.bundlejava.user.exception.UserAlreadyExistsException;
import com.akveo.bundlejava.user.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    private RoleService roleService;
    private ImageRepository imageRepository;
    private SettingsService settingsService;
    private TokenValidationService tokenValidationService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ModelMapper modelMapper,
                       SettingsService settingsService,
                       RoleService roleService,
                       ImageRepository imageRepository,
                       TokenValidationService tokenValidationService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.settingsService = settingsService;
        this.roleService = roleService;
        this.imageRepository = imageRepository;
        this.tokenValidationService = tokenValidationService;
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
        //Initialize user's default picture
        String baseStr = "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";
        userDTO.setBaseString(baseStr);

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

    private Image convertBaseStringToImage(String baseString) {
        Image userImage = new Image();
        byte[] decodedString = Base64.getDecoder().decode(baseString.getBytes());
        userImage.setImageBytes(decodedString);
        return userImage;
    }

    private String convertImageToBaseString(Image image) {
        byte[] bytes = image.getImageBytes();
        byte[] encodedString = Base64.getEncoder().encode(bytes);
        return new String(encodedString);
    }

    private UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundHttpException(
                        "User with id: " + id + " not found", HttpStatus.NOT_FOUND)
                );
        User updatedUser = modelMapper.map(userDTO, User.class);
        Image image = convertBaseStringToImage(userDTO.getBaseString());
        image.setId(updatedUser.getId());
        image.setUser(updatedUser);
        updatedUser.setImage(image);
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
        user.setLogin(signUpDTO.getFullName());
        String encodedPassword = encodePassword(signUpDTO.getPassword());
        user.setPasswordHash(encodedPassword);
        user.setRoles(new HashSet<>(Collections.singletonList(roleService.getDefaultRole())));
        user.setSettings(new Settings("Cosmic"));
        return user;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Image getImageById(Long id, String token) {

        try {
            tokenValidationService.isValid(token);
        } catch (Exception e) {
            throw new AccessTokenNotFoundHttpException("Access token wasn't found", HttpStatus.NOT_FOUND);
        }

        Image existingImage = imageRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException("image is not exist")
                );
        return existingImage;
    }

    public Image updateUserImageById(Long id, String baseString) {
        imageRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Test exception"));
        Image existingImage = convertBaseStringToImage(baseString);
        existingImage.setId(id);
        imageRepository.save(existingImage);
        return existingImage;
    }

}
