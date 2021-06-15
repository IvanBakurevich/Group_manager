package com.github.ivanbakurevich.groupmanager.rest.dto;

import lombok.Data;

@Data
public class SuccessAuthenticationDto {
    private String username;
    private String token;
}
