package com.akveo.bundlejava.user;

import com.akveo.bundlejava.authentication.RegisterRequest;
import com.akveo.bundlejava.authentication.exception.UserNotFoundHttpException;
import com.akveo.bundlejava.role.Role;
import com.akveo.bundlejava.user.exception.UserAlreadyExistsException;
import com.akveo.bundlejava.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User with email: " + email + " not found");
        }

        return user;
    }

    @Transactional
    public User register(RegisterRequest registerRequest) throws UserAlreadyExistsException {
        if (emailExists(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException(registerRequest.getEmail());
        }

        User user = createUser(registerRequest);

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
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }

        return convertUser(userOptional.get());
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundHttpException("User with id: " + userId + " not found", HttpStatus.NOT_FOUND);
        }

//        TODO add mappings
        // Update user details
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUserName(userDTO.getUserName());
        user.setAge(userDTO.getAge());
//        TODO Adress
        user.setRoles(userDTO.getRoles());

        userRepository.save(user);
        return userDTO;
    }

    @Transactional
    public boolean deleteUser(Long id) {
//        TODO test if user not found
        userRepository.deleteById(id);
        return true;
    }

    public UserDTO getCurrentUser() {
        User user = UserContextHolder.getUser();
        return convertUser(user);
    }

//    TODO use common logic with previous update
    public UserDTO updateCurrentUser(UserDTO userDTO) {
        User user = UserContextHolder.getUser();
        Long id = user.getId();

        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }

//        TODO add mappings
        // Update user details
        User modifiedUser = new User();
        modifiedUser.setFirstName(userDTO.getFirstName());
        modifiedUser.setLastName(userDTO.getLastName());
        modifiedUser.setUserName(userDTO.getUserName());
        modifiedUser.setAge(userDTO.getAge());
//        TODO Adress
        modifiedUser.setRoles(userDTO.getRoles());

        userRepository.save(modifiedUser);
        return userDTO;
    }

    private boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    private User createUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());

        String encodedPassword = encodePassword(registerRequest.getPassword());
        user.setPasswordHash(encodedPassword);
        user.setUserName(registerRequest.getFullName());

        Role role = new Role();
        role.setName("User");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));

        return user;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private UserDTO convertUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        return userDTO;
    }
}
