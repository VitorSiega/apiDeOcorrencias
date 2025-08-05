package com.example.aplicativo.usuario.dto;

import com.example.aplicativo.usuario.enums.RoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDTO(
        @NotBlank(message = "Preencha o nome") String nome,
        @NotBlank(message = "Não pode estar sem telefone") String telefone,
        @NotBlank(message = "Preencha a unidade do usuário") String unidade,
        @NotNull(message = "Preencha a permissão do usuário") RoleEnum roleUser,
        @Email(message = "E-mail inválido!") @NotBlank(message = "Preencha o e-mail!") String email,
        @NotBlank(message = "Preencha a senha!") String senha,
        @NotNull(message = "Não pode estar sem a informação de login") Boolean usuarioComLogin) {
}
