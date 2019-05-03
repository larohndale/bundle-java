/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        return ok(userService.getUserById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUserById(id, userDTO);
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

    @PutMapping("/current")
    public ResponseEntity updateCurrentUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateCurrentUser(userDTO);
        return ok(updatedUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity createUsers(@Valid @RequestBody UserDTO userDTO) {
        return ok(userService.createUser(userDTO));
    }

}
