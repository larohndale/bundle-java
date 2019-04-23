package com.akveo.bundlejava.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        return ok(null);
    }
}
