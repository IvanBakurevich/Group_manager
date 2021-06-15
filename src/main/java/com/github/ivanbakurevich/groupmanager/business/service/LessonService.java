package com.github.ivanbakurevich.groupmanager.business.service;

import com.github.ivanbakurevich.groupmanager.rest.dto.TimetableLessonDto;

import java.util.List;

public interface LessonService {

    List<TimetableLessonDto> getTimetableByUsername(String username);
}

