package com.maosmeo.maolibreria.repository;

import com.maosmeo.maolibreria.repository.entity.RoleEntity;
import com.maosmeo.maolibreria.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

}
