package com.github.ivanbakurevich.groupmanager.dao.repository;

import com.github.ivanbakurevich.groupmanager.dao.entity.ConcreteSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConcreteSubjectRepository extends JpaRepository<ConcreteSubjectEntity, Integer> {

//    @Query(nativeQuery = true, value = "" +
//            "   select * from concrete_subject cs")
//    List<ConcreteSubjectEntity> getAvailableByUsername(String username);
}
