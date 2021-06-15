package com.github.ivanbakurevich.groupmanager.business.service.impl;

import com.github.ivanbakurevich.groupmanager.business.service.LessonService;
import com.github.ivanbakurevich.groupmanager.dao.repository.LessonRepository;
import com.github.ivanbakurevich.groupmanager.rest.dto.TimetableLessonDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TimetableLessonDto> getTimetableByUsername(String username) {
        return lessonRepository.getAllByUsername(username).stream()
                .map(l -> modelMapper.map(l, TimetableLessonDto.class))
                .distinct()
                .collect(Collectors.toList());
    }
}
