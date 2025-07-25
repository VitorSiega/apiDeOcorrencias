package com.example.aplicativo.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aplicativo.usuario.enums.RoleEnum;
import com.example.aplicativo.usuario.model.RoleFuncModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleFuncModel, Long> {
    Optional<RoleFuncModel> findByName(RoleEnum name);
}
