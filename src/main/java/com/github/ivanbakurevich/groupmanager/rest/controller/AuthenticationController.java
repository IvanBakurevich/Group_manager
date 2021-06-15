package com.github.ivanbakurevich.groupmanager.rest.controller;


import com.github.ivanbakurevich.groupmanager.business.service.OrganizationService;
import com.github.ivanbakurevich.groupmanager.business.service.UserService;
import com.github.ivanbakurevich.groupmanager.dao.entity.UserEntity;
import com.github.ivanbakurevich.groupmanager.rest.dto.*;
import com.github.ivanbakurevich.groupmanager.security.jwt.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;
    private final OrganizationService organizationService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, OrganizationService organizationService, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.organizationService = organizationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public SuccessAuthenticationDto login(@RequestBody @Validated UserLoginDto requestDto) {
        try {
            String username = requestDto.getUsername();
            UserEntity user = userService.findByUsername(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRole());

            SuccessAuthenticationDto authDto = new SuccessAuthenticationDto();
            authDto.setUsername(username);
            authDto.setToken(token);


            return authDto;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping(path = "/register")
    public UserDto registerStudent(@RequestBody @Validated UserDto newUser) {
        return userService.registerStudent(newUser);
    }

    @PostMapping(path = "/registerOrganization")
    public boolean registerOrganization(@RequestBody CreateOrganizationDto organizationDto) {
        OrganizationDto organization = organizationService.createOrganization(organizationDto);
        UserDto user = userService.registerLocalAdmin(modelMapper.map(organizationDto, UserDto.class));
        return (organization != null && user != null);
    }
}
