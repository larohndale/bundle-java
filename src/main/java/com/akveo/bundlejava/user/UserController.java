package com.akveo.bundlejava.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        return ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        return ok(userService.deleteUser(id));
    }

    @GetMapping("/current")
    public ResponseEntity getCurrentUser() {
        return ok(userService.getCurrentUser());
    }

    @PutMapping("/{current")
    public ResponseEntity updateCurrentUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateCurrentUser(userDTO);
        return ok(updatedUser);
    }

    @PostMapping("")
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userDTO) {
//        TODO complete
//        userService.register()
        return ok(null);
    }

}
