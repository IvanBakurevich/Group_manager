package com.github.ivanbakurevich.groupmanager.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserLoginDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
