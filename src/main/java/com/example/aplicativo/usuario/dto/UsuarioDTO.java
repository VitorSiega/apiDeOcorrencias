package com.example.aplicativo.usuario.dto;

import com.example.aplicativo.usuario.enums.RoleEnum;

public record UsuarioDTO(
        String nome,
        String telefone,
        String unidade,
        RoleEnum roleUser,
        String email,
        String senha) {
}
