package com.github.ivanbakurevich.groupmanager.dao.repository;

import com.github.ivanbakurevich.groupmanager.dao.entity.OrganizationEntity;
import com.github.ivanbakurevich.groupmanager.dao.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    List<SubjectEntity> findAllByOrganization(OrganizationEntity organization);

    @Query(nativeQuery = true, value = "" +
            " select * from subject S " +
            " where S.organization_id in (:ids) ")
    List<SubjectEntity> findAllByOrganizationIds(List<Integer> ids);

    @Query(nativeQuery = true, value = "" +
            " select * from subject " +
            " where subject.is_public = 1 ")
    List<SubjectEntity> findAllPublic();

    Optional<SubjectEntity> findByTitleAndOrganization(String title, OrganizationEntity organization);
}
