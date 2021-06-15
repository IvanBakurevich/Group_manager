package com.github.ivanbakurevich.groupmanager.dao.repository;

import com.github.ivanbakurevich.groupmanager.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    @Query(nativeQuery = true, value = "" +
            " select distinct ou.organization_id from user U " +
            " join organization_user ou on U.id = ou.user_id " +
            " where U.username = :username ")
    List<Integer> getOrganizationIds(String username);

    @Query(nativeQuery = true, value = "" +
            " select * from user" +
            " join organization_user ou on user.id = ou.user_id" +
            " where ou.organization_id = :id")
    List<UserEntity> getByOrganizationId(Integer id);

    @Query(nativeQuery = true, value = "" +
            "select * from user ")
    List<UserEntity> getAll();
}
