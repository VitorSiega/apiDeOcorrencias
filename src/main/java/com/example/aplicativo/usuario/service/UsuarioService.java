package com.example.aplicativo.usuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.aplicativo.usuario.dto.UsuarioDTO;
import com.example.aplicativo.usuario.model.RoleFunc;
import com.example.aplicativo.usuario.model.RoleUser;
import com.example.aplicativo.usuario.model.Usuario;
import com.example.aplicativo.usuario.repository.RoleRepository;
import com.example.aplicativo.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
    }

    public void criarUsuario(UsuarioDTO usuarioDTO) {

        RoleFunc roleNewUser = roleRepository.findByName(usuarioDTO.roleUser())
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        RoleUser roleUser = RoleUser.builder()
                .role(roleNewUser)
                .build();

        Usuario usuario = Usuario.builder()
                .nome(usuarioDTO.nome())
                .telefone(usuarioDTO.telefone())
                .unidade(usuarioDTO.unidade())
                .role(roleUser)
                .build();

        usuarioRepository.save(usuario);

    }

    public void atualizarUsuario() {

    }

    public List listUser() {
        return usuarioRepository.findAll();
    }

}
