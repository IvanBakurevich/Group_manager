package com.github.ivanbakurevich.groupmanager.rest.controller;

import com.github.ivanbakurevich.groupmanager.business.service.TopicService;
import com.github.ivanbakurevich.groupmanager.rest.dto.TopicDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<TopicDto> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping(path = "/organization/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN','TEACHER','STUDENT')")
    public List<TopicDto> getAllTopicsByOrganization(@PathVariable("id") Integer organizationId, Authentication authentication) {
        return topicService.getAllTopicsByOrganizationId(organizationId, authentication.getName());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public TopicDto createTopic(@RequestBody @Validated TopicDto topicDto, Authentication authentication) {
        return topicService.addTopic(topicDto, authentication.getName());
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public TopicDto updateTopic(@RequestBody @Validated TopicDto topicDto, Authentication authentication) {
        return topicService.updateTopic(topicDto, authentication.getName());
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','LOCAL_ADMIN')")
    public boolean deleteTopic(@PathVariable("id") Integer id, Authentication authentication) {
        return topicService.deleteTopic(id, authentication.getName());
    }
}
