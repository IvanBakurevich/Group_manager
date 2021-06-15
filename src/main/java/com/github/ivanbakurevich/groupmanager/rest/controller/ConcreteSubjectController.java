package com.github.ivanbakurevich.groupmanager.rest.controller;

import com.github.ivanbakurevich.groupmanager.business.service.ConcreteSubjectService;
import com.github.ivanbakurevich.groupmanager.rest.dto.ConcreteSubjectDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/concrete-subjects")
@AllArgsConstructor
public class ConcreteSubjectController {

    private final ConcreteSubjectService concreteSubjectService;

    @GetMapping(path = "/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<ConcreteSubjectDto> getAllConcreteSubjects() {
        return concreteSubjectService.getAllConcreteSubjects();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN','TEACHER','STUDENT')")
    public List<ConcreteSubjectDto> getAllConcreteSubjects(Authentication authentication) {
        return concreteSubjectService.getAvailableConcreteSubjects(authentication.getName());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public ConcreteSubjectDto createConcreteSubjects(@RequestBody ConcreteSubjectDto concreteSubjectDto, Authentication authentication) {
        return concreteSubjectService.createConcreteSubject(concreteSubjectDto, authentication.getName());
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public boolean deleteConcreteSubjects(@PathVariable Integer id, Authentication authentication) {
        return concreteSubjectService.deleteConcreteSubject(id, authentication.getName());
    }


    @PostMapping(path = "/{concreteSubjectId}/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public boolean addTopicToSubject(@PathVariable("concreteSubjectId") Integer concreteSubjectId,
                                     @PathVariable("studentId") Integer studentId, Authentication authentication) {
        return concreteSubjectService.addStudentToConcreteSubject(studentId, concreteSubjectId, authentication.getName());
    }

    @DeleteMapping(path = "/{concreteSubjectId}/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public boolean deleteTopicFromSubject(@PathVariable("concreteSubjectId") Integer concreteSubjectId,
                                          @PathVariable("studentId") Integer studentId, Authentication authentication) {
        return concreteSubjectService.deleteStudentFromConcreteSubject(studentId, concreteSubjectId, authentication.getName());
    }

    @PostMapping(path = "/{concreteSubjectId}/join")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public boolean addTopicToSubject(@PathVariable("concreteSubjectId") Integer concreteSubjectId, Authentication authentication) {
        return concreteSubjectService.joinConcreteSubject(concreteSubjectId, authentication.getName());
    }

    @DeleteMapping(path = "/{concreteSubjectId}/leave")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public boolean deleteTopicFromSubject(@PathVariable("concreteSubjectId") Integer concreteSubjectId, Authentication authentication) {
        return concreteSubjectService.leaveConcreteSubject(concreteSubjectId, authentication.getName());
    }

}
