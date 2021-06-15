package com.github.ivanbakurevich.groupmanager.dao.repository;

import com.github.ivanbakurevich.groupmanager.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
    RoleEntity findByRoleName(String name);
}
