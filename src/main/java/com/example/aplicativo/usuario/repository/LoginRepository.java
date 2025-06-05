package com.example.aplicativo.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aplicativo.usuario.model.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {

}
