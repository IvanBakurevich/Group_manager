package com.github.ivanbakurevich.groupmanager.rest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    @NotNull(message = "Username required")
    private String username;
    private String email;
    @NotNull(message = "Password required")
    private String password;
    private String role;

}
