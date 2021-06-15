package com.github.ivanbakurevich.groupmanager.rest.controller;

import com.github.ivanbakurevich.groupmanager.business.service.UserService;
import com.github.ivanbakurevich.groupmanager.rest.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public UserDto addTeacherOrStudent(@RequestBody @Validated UserDto newUser) {
        return userService.createTeacherOrStudent(newUser);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/organization/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public List<UserDto> getAllUsersByOrganizationId(@PathVariable Integer id, Authentication authentication) {
        return userService.getUsersByOrganizationId(id, authentication.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean deleteUser(@PathVariable("id") Integer id) {
        return userService.deleteUser(id);
    }
}
