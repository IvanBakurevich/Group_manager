package com.github.ivanbakurevich.groupmanager.business.service;

import com.github.ivanbakurevich.groupmanager.dao.entity.SubjectEntity;
import com.github.ivanbakurevich.groupmanager.rest.dto.TopicDto;

import java.util.List;

public interface TopicService {

    List<TopicDto> getAllTopics();

    List<TopicDto> getAllTopicsByOrganizationId(Integer organizationId, String username);

    //List<TopicDto> getAllTopicsBySubjectId(Integer subjectId, String username);

    TopicDto addTopic(TopicDto topic, String username);

    boolean deleteTopic(Integer id, String username);

    TopicDto updateTopic(TopicDto topicDto, String username);
}
