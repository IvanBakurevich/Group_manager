package com.github.ivanbakurevich.groupmanager.business.service;

import com.github.ivanbakurevich.groupmanager.dao.entity.UserEntity;
import com.github.ivanbakurevich.groupmanager.rest.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerStudent(UserDto user);

    UserDto registerLocalAdmin(UserDto user);

    UserDto createTeacherOrStudent(UserDto newUser);

    List<UserDto> getAllUsers();

    boolean isUserInOrganization(String username, Integer organizationId);

    UserEntity findByUsername(String username);

    UserEntity findById(Integer id);

    boolean deleteUser(Integer id);

    List<UserDto> getUsersByOrganizationId(Integer id, String username);
}
