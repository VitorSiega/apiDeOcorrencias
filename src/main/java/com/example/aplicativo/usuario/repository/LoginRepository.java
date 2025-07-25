package com.example.aplicativo.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aplicativo.usuario.model.LoginModel;

@Repository
public interface LoginRepository extends JpaRepository<LoginModel, Long> {
    Optional<LoginModel> findByEmail(String email);
}
