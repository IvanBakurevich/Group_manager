package com.github.ivanbakurevich.groupmanager.rest.controller;

import com.github.ivanbakurevich.groupmanager.business.service.LessonService;
import com.github.ivanbakurevich.groupmanager.rest.dto.TimetableLessonDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/lessons")
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping(path = "/timetable")
    @PreAuthorize("hasAnyAuthority('TEACHER','STUDENT')")
    public List<TimetableLessonDto> getTimetable(Authentication authentication) {
        return lessonService.getTimetableByUsername(authentication.getName());
    }
}
