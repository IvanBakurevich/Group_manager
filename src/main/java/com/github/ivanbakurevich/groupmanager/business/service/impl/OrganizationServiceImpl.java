package com.github.ivanbakurevich.groupmanager.business.service.impl;

import com.github.ivanbakurevich.groupmanager.business.service.OrganizationService;
import com.github.ivanbakurevich.groupmanager.business.service.UserService;
import com.github.ivanbakurevich.groupmanager.dao.entity.OrganizationEntity;
import com.github.ivanbakurevich.groupmanager.dao.entity.UserEntity;
import com.github.ivanbakurevich.groupmanager.dao.repository.OrganizationRepository;
import com.github.ivanbakurevich.groupmanager.dao.repository.UserRepository;
import com.github.ivanbakurevich.groupmanager.rest.dto.OrganizationDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, UserRepository userRepository, UserService userService, ModelMapper modelMapper) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrganizationDto createOrganization(OrganizationDto organizationDto) {
        OrganizationEntity org = modelMapper.map(organizationDto, OrganizationEntity.class);
        organizationRepository.save(org);
        return organizationDto;
    }

    @Override
    public OrganizationDto updateOrganization(OrganizationDto organizationDto) {
        Optional<OrganizationEntity> byId = organizationRepository.findById(organizationDto.getId());
        if (byId.isPresent()) {
            organizationRepository.save(byId.get());
            return organizationDto;
        }
        return null;
    }

    @Override
    public List<OrganizationDto> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(org -> modelMapper.map(org, OrganizationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeOrganization(Integer organizationId) {
        Optional<OrganizationEntity> byId = organizationRepository.findById(organizationId);
        byId.ifPresent(organizationRepository::delete);
    }

    @Override
    public OrganizationDto getById(Integer id) {
        Optional<OrganizationEntity> byId = organizationRepository.findById(id);
        if (byId.isPresent()) {
            OrganizationEntity organizationEntity = byId.get();
            return modelMapper.map(organizationEntity, OrganizationDto.class);
        }
        return null;
    }

    @Override
    public boolean addStudentToOrganization(Integer studentId, Integer organizationId, String username) {
        Optional<OrganizationEntity> orgOpt = organizationRepository.findById(organizationId);
        Optional<UserEntity> userOpt = userRepository.findById(studentId);

        if (orgOpt.isPresent() && userOpt.isPresent()
                && userService.isUserInOrganization(username, orgOpt.get().getId())) {
            OrganizationEntity organization = orgOpt.get();
            List<UserEntity> users = organization.getUsers();
            if (!users.contains(userOpt.get())) {
                users.add(userOpt.get());
                organization.setUsers(users);
                organizationRepository.save(organization);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeStudentFromOrganization(Integer studentId, Integer organizationId, String username) {
        Optional<OrganizationEntity> orgOpt = organizationRepository.findById(organizationId);
        Optional<UserEntity> userOpt = userRepository.findById(studentId);

        if (orgOpt.isPresent() && userOpt.isPresent()
                && userService.isUserInOrganization(username, orgOpt.get().getId())) {
            OrganizationEntity organization = orgOpt.get();
            List<UserEntity> users = organization.getUsers();
            if (users.contains(userOpt.get())) {
                users.remove(userOpt.get());
                organization.setUsers(users);
                organizationRepository.save(organization);
                return true;
            }
        }
        return false;
    }
}
