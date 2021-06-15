package com.github.ivanbakurevich.groupmanager.business.service.impl;

import com.github.ivanbakurevich.groupmanager.business.service.UserService;
import com.github.ivanbakurevich.groupmanager.dao.entity.RoleEntity;
import com.github.ivanbakurevich.groupmanager.dao.entity.UserEntity;
import com.github.ivanbakurevich.groupmanager.dao.repository.RoleRepository;
import com.github.ivanbakurevich.groupmanager.dao.repository.UserRepository;
import com.github.ivanbakurevich.groupmanager.rest.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto registerStudent(UserDto user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setRole(roleRepository.findByRoleName("STUDENT"));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        if (userRepository.findById(userEntity.getId()).isEmpty()
                && userRepository.findByUsername(userEntity.getUsername()).isEmpty()) {
            UserEntity registeredUser = userRepository.save(userEntity);
            return modelMapper.map(registeredUser, UserDto.class);
        }
        return null;
    }

    @Override
    public UserDto registerLocalAdmin(UserDto user) {
        return null;
    }

    @Override
    public UserDto createTeacherOrStudent(UserDto user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        RoleEntity studentRole = roleRepository.findByRoleName("STUDENT");
        RoleEntity teacherRole = roleRepository.findByRoleName("TEACHER");

        List<RoleEntity> availableRoles = new ArrayList<>();
        availableRoles.add(studentRole);
        availableRoles.add(teacherRole);

        if (!availableRoles.contains(userEntity.getRole())) {
            return null;
        }

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        if (userRepository.findById(userEntity.getId()).isEmpty()
                && userRepository.findByUsername(userEntity.getUsername()).isEmpty()) {
            UserEntity registeredUser = userRepository.save(userEntity);
            return modelMapper.map(registeredUser, UserDto.class);
        }
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAll().stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity findByUsername(String username) {
        Optional<UserEntity> opt = userRepository.findByUsername(username);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    @Override
    public UserEntity findById(Integer id) {
        Optional<UserEntity> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    @Override
    public boolean deleteUser(Integer id) {
        Optional<UserEntity> opt = userRepository.findById(id);
        if (opt.isPresent()
                && opt.get().getRole().equals(roleRepository.findByRoleName("STUDENT"))
                && opt.get().getRole().equals(roleRepository.findByRoleName("TEACHER"))) {
            userRepository.delete(opt.get());
            return true;
        }
        return false;
    }

    @Override
    public List<UserDto> getUsersByOrganizationId(Integer id, String username) {
        List<Integer> organizationIds = userRepository.getOrganizationIds(username);
        if (organizationIds.contains(id)) {
            return userRepository.getByOrganizationId(id).stream()
                    .map(u -> modelMapper.map(u, UserDto.class))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public boolean isUserInOrganization(String username, Integer organizationId) {
        return userRepository.getOrganizationIds(username).contains(organizationId);
    }
}
