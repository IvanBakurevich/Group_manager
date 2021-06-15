package com.github.ivanbakurevich.groupmanager.dao.repository;

import com.github.ivanbakurevich.groupmanager.dao.entity.OrganizationEntity;
import com.github.ivanbakurevich.groupmanager.dao.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {


    Optional<TopicEntity> findById(Integer id);

    TopicEntity findByTitle(Integer id);

    List<TopicEntity> findAllByOrganization(OrganizationEntity organization);

    @Query(nativeQuery = true, value = "" +
            "   SELECT * FROM topic T " +
            "   JOIN subject_topic ST ON ST.topic_id = T.id " +
            "   WHERE ST.subject_id = :subjectId")
    List<TopicEntity> findAllBySubjectId(Integer subjectId);

    @Query(nativeQuery = true, value = "" +
            "   SELECT * FROM topic T " +
            "   JOIN subject_topic ST ON ST.topic_id = T.id " +
            "   JOIN subject S ON S.id = ST.subject_id " +
            "   WHERE S.title = :title" +
            "   AND S.organization_id = :organizationId ")
    List<TopicEntity> findAllBySubjectTitleAndOrganizationId(String title, Integer organizationId);
}
