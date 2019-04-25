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

    @PutMapping
    public ResponseEntity updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        return ok(null);
    }

}
