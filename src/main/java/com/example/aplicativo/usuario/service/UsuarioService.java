package com.example.aplicativo.usuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.aplicativo.usuario.dto.UsuarioDTO;
import com.example.aplicativo.usuario.enums.RoleEnum;
import com.example.aplicativo.usuario.model.Login;
import com.example.aplicativo.usuario.model.RoleFunc;
import com.example.aplicativo.usuario.model.RoleUser;
import com.example.aplicativo.usuario.model.Usuario;
import com.example.aplicativo.usuario.repository.LoginRepository;
import com.example.aplicativo.usuario.repository.RoleRepository;
import com.example.aplicativo.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final LoginRepository loginRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository,
            LoginRepository loginRepository) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.loginRepository = loginRepository;
    }

    public void criarUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuario = Usuario.builder()
                .nome(usuarioDTO.nome())
                .telefone(usuarioDTO.telefone())
                .unidade(usuarioDTO.unidade())
                .role(verificaRole(usuarioDTO.roleUser()))
                .build();

        usuarioRepository.save(usuario);

        Login login = Login.builder()
                .user(usuario)
                .email(usuarioDTO.email())
                .senha(usuarioDTO.senha())
                .build();

        loginRepository.save(login);

    }

    public void atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario userAtual = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        userAtual.setNome(usuarioDTO.nome());
        userAtual.setTelefone(usuarioDTO.telefone());
        userAtual.setUnidade(usuarioDTO.unidade());
        userAtual.setRole(verificaRole(usuarioDTO.roleUser()));

        usuarioRepository.save(userAtual);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<Usuario> listUser() {
        return usuarioRepository.findAll();
    }

    private RoleUser verificaRole(RoleEnum role) {
        RoleFunc roleNewUser = roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        RoleUser roleUser = RoleUser.builder()
                .role(roleNewUser)
                .build();
        return roleUser;
    }

}
