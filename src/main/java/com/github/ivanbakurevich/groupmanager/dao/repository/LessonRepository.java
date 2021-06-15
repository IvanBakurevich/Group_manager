package com.github.ivanbakurevich.groupmanager.dao.repository;

import com.github.ivanbakurevich.groupmanager.dao.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<LessonEntity, Integer> {

    @Query(nativeQuery = true, value = "" +
            "select distinct * " +
            "from lesson L " +
            "         join concrete_subject CS on L.concrete_subject_id = CS.id " +
            "         join group_user gu on CS.group_id = gu.group_id " +
            "         join user u on gu.user_id = u.id " +
            "        join user u2 on CS.teacher_id = u2.id " +
            "where (u.username = :username or u2.username = :username) " +
            "order by L.datetime")
    List<LessonEntity> getAllByUsername(String username);

}
