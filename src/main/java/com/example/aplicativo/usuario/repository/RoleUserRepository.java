package com.example.aplicativo.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aplicativo.usuario.model.RoleUserModel;

public interface RoleUserRepository extends JpaRepository<RoleUserModel, Long> {

}
