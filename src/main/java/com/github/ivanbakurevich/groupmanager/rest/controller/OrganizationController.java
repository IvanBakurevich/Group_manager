package com.github.ivanbakurevich.groupmanager.rest.controller;

import com.github.ivanbakurevich.groupmanager.business.service.OrganizationService;
import com.github.ivanbakurevich.groupmanager.rest.dto.CreateOrganizationDto;
import com.github.ivanbakurevich.groupmanager.rest.dto.OrganizationDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<OrganizationDto> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public OrganizationDto getOrganizationById(@PathVariable Integer id) {
        return organizationService.getById(id);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public OrganizationDto updateOrganization(@RequestBody CreateOrganizationDto organizationDto) {
        return organizationService.updateOrganization(organizationDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void removeOrganization(Integer id) {
        organizationService.removeOrganization(id);
    }


    @PostMapping(path = "/{organizationId}/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public boolean addStudentAccessToOrganization(@PathVariable Integer organizationId,
                                                  @PathVariable Integer studentId, Authentication authentication) {
        return organizationService.addStudentToOrganization(studentId,organizationId,authentication.getName());
    }

    @DeleteMapping(path = "/{organizationId}/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public boolean removeStudentAccessToOrganization(@PathVariable Integer organizationId,
                                                  @PathVariable Integer studentId, Authentication authentication) {
        return organizationService.removeStudentFromOrganization(studentId,organizationId,authentication.getName());
    }
}
