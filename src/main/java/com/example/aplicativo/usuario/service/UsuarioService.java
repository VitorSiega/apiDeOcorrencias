package com.example.aplicativo.usuario.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.aplicativo.login.service.JwtTokenService;
import com.example.aplicativo.usuario.dto.UsuarioDTO;
import com.example.aplicativo.usuario.enums.RoleEnum;
import com.example.aplicativo.usuario.model.LoginModel;
import com.example.aplicativo.usuario.model.RoleFuncModel;
import com.example.aplicativo.usuario.model.RoleUserModel;
import com.example.aplicativo.usuario.model.UsuarioModel;
import com.example.aplicativo.usuario.repository.LoginRepository;
import com.example.aplicativo.usuario.repository.RoleRepository;
import com.example.aplicativo.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository,
            LoginRepository loginRepository, PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    // public void criarUsuario(UsuarioDTO usuarioDTO) {

    // UsuarioModel usuario = UsuarioModel.builder()
    // .nome(usuarioDTO.nome())
    // .telefone(usuarioDTO.telefone())
    // .unidade(usuarioDTO.unidade())
    // .role(verificaRole(usuarioDTO.roleUser()))
    // .build();

    // usuarioRepository.save(usuario);

    // LoginModel login = LoginModel.builder()
    // .user(usuario)
    // .email(usuarioDTO.email())
    // .senha(passwordEncoder.encode(usuarioDTO.senha()))
    // .build();

    // loginRepository.save(login);

    // }

    public void criarUsuario(UsuarioDTO usuarioDTO) {
        UsuarioModel novoUsuario = criarUsuarioBase(usuarioDTO);
        usuarioRepository.save(novoUsuario);
    }

    public void criarUsuarioComLogin(UsuarioDTO usuarioDTO) {
        UsuarioModel novoUsuario = criarUsuarioBase(usuarioDTO);
        usuarioRepository.save(novoUsuario);
        LoginModel novoLogin = criarLoginUsuario(usuarioDTO, novoUsuario);
        loginRepository.save(novoLogin);
    }

    private UsuarioModel criarUsuarioBase(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.usuarioComLogin())
            verificarDadosUsuario(usuarioDTO);

        UsuarioModel usuario = UsuarioModel.builder()
                .nome(usuarioDTO.nome())
                .telefone(usuarioDTO.telefone())
                .unidade(usuarioDTO.unidade())
                .role(verificaRole(usuarioDTO.roleUser()))
                .build();

        return usuario;
    }

    private LoginModel criarLoginUsuario(UsuarioDTO usuarioDTO, UsuarioModel novoUsuario) {
        LoginModel novoLogin = LoginModel.builder()
                .email(usuarioDTO.email())
                .senha(passwordEncoder.encode(usuarioDTO.senha()))
                .user(novoUsuario)
                .build();

        return novoLogin;
    }

    // função para verificar se todos os dados do cliente/usuario foram preenchidos
    // corretamente
    private void verificarDadosUsuario(UsuarioDTO usuarioDTO) {
        if (loginRepository.findByEmail(usuarioDTO.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já existente!");
        }
    }

    // Administrador atualiza o usuario
    public void atualizarUsuarioAtual(UsuarioDTO usuarioDTO, String authorization) {
        Long id = jwtTokenService.pegarId(authorization.substring(7));
        UsuarioModel userAtual = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        userAtual.setNome(usuarioDTO.nome());
        userAtual.setTelefone(usuarioDTO.telefone());
        userAtual.setUnidade(usuarioDTO.unidade());
        userAtual.setRole(verificaRole(usuarioDTO.roleUser()));

        usuarioRepository.save(userAtual);
    }

    // Atualiza o propio usuario, função de usuario
    public void atualizarUsuario(UsuarioDTO usuarioDTO, Long id) {
        UsuarioModel userAtual = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        userAtual.setNome(usuarioDTO.nome());
        userAtual.setTelefone(usuarioDTO.telefone());
        userAtual.setUnidade(usuarioDTO.unidade());

        usuarioRepository.save(userAtual);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioModel buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<UsuarioModel> listUser() {
        return usuarioRepository.findAll();
    }

    private RoleUserModel verificaRole(RoleEnum role) {
        RoleFuncModel roleNewUser = roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        RoleUserModel roleUser = RoleUserModel.builder()
                .role(roleNewUser)
                .build();
        return roleUser;
    }

}
