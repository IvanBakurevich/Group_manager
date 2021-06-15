package com.github.ivanbakurevich.groupmanager.rest.controller;

import com.github.ivanbakurevich.groupmanager.business.service.SubjectService;
import com.github.ivanbakurevich.groupmanager.business.service.UserService;
import com.github.ivanbakurevich.groupmanager.rest.dto.SubjectDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final UserService userService;

    public SubjectController(SubjectService subjectService, UserService userService) {
        this.subjectService = subjectService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN','TEACHER','STUDENT')")
    public List<SubjectDto> getAvailableSubjects(@RequestParam(value = "private-only", required = false, defaultValue = "false") boolean onlyPrivate,
                                                 Authentication authentication) {
        if (onlyPrivate) {
            return subjectService.getPrivateSubjectsByUsername(authentication.getName());
        }
        return subjectService.getSubjectsByUsername(authentication.getName());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN','TEACHER','STUDENT')")
    public SubjectDto getSubject(@PathVariable("id") Integer id, Authentication authentication) {
        return subjectService.getSubjectById(id, authentication.getName());
    }

    @GetMapping(path = "/organization/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public List<SubjectDto> getSubjectsByOrganization(@PathVariable("id") Integer organizationId, Authentication authentication) {
        return subjectService.getSubjectsByOrganizationId(organizationId, authentication.getName());
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public SubjectDto createSubject(@RequestBody @Validated SubjectDto subjectDto, Authentication authentication) {
        return subjectService.addSubject(subjectDto, authentication.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public boolean removeSubject(@PathVariable("id") Integer id, Authentication authentication) {
        return subjectService.deleteSubject(id, authentication.getName());
    }

    @PostMapping(path = "/{subjectId}/topic/{topicId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public boolean addTopicToSubject(@PathVariable("subjectId") Integer subjectId,
                                     @PathVariable("topicId") Integer topicId, Authentication authentication) {
        return subjectService.addTopicToSubject(topicId, subjectId, authentication.getName());
    }

    @DeleteMapping(path = "/{subjectId}/topic/{topicId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public boolean deleteTopicFromSubject(@PathVariable("subjectId") Integer subjectId,
                                          @PathVariable("topicId") Integer topicId, Authentication authentication) {
        return subjectService.deleteTopicFromSubject(topicId, subjectId, authentication.getName());
    }
}
