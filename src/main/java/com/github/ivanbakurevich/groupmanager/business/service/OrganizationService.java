package com.github.ivanbakurevich.groupmanager.business.service;

import com.github.ivanbakurevich.groupmanager.rest.dto.OrganizationDto;

import java.util.List;

public interface OrganizationService {

    OrganizationDto createOrganization(OrganizationDto organizationDto);

    OrganizationDto updateOrganization(OrganizationDto organizationDto);

    List<OrganizationDto> getAllOrganizations();

    void removeOrganization(Integer organizationId);

    OrganizationDto getById(Integer id);

    boolean addStudentToOrganization(Integer studentId, Integer organizationId, String username);

    boolean removeStudentFromOrganization(Integer studentId, Integer organizationId, String username);
}
