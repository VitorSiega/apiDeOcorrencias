package com.example.aplicativo.usuario.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.aplicativo.login.model.LoginModel;
import com.example.aplicativo.login.repository.LoginRepository;
import com.example.aplicativo.login.service.JwtTokenService;
import com.example.aplicativo.usuario.dto.AtualizacaoUsuarioDTO;
import com.example.aplicativo.usuario.dto.UsuarioDTO;
import com.example.aplicativo.usuario.enums.RoleEnum;
import com.example.aplicativo.usuario.model.RoleFuncModel;
import com.example.aplicativo.usuario.model.RoleUserModel;
import com.example.aplicativo.usuario.model.UsuarioModel;
import com.example.aplicativo.usuario.repository.RoleRepository;
import com.example.aplicativo.usuario.repository.RoleUserRepository;
import com.example.aplicativo.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleUserRepository roleUserRepository;
    private final RoleRepository roleRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository,
            LoginRepository loginRepository, PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService,
            RoleUserRepository roleUserRepository) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.roleUserRepository = roleUserRepository;
    }

    // cria um usuario sem login
    public void criarUsuario(UsuarioDTO usuarioDTO) {
        UsuarioModel novoUsuario = criarUsuarioBase(usuarioDTO);
        usuarioRepository.save(novoUsuario);
    }

    // cria um usuario com login
    public void criarUsuarioComLogin(UsuarioDTO usuarioDTO) {
        UsuarioModel novoUsuario = criarUsuarioBase(usuarioDTO);
        usuarioRepository.save(novoUsuario);
        LoginModel novoLogin = criarLoginUsuario(usuarioDTO, novoUsuario);
        loginRepository.save(novoLogin);
    }

    // cria um usuario
    private UsuarioModel criarUsuarioBase(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.usuarioComLogin())
            verificarDadosUsuarioCriado(usuarioDTO);

        UsuarioModel usuario = UsuarioModel.builder()
                .nome(usuarioDTO.nome())
                .telefone(usuarioDTO.telefone())
                .unidade(usuarioDTO.unidade())
                .role(verificaRole(usuarioDTO.roleUser()))
                .build();

        return usuario;
    }

    // cria o login do usuario
    private LoginModel criarLoginUsuario(UsuarioDTO usuarioDTO, UsuarioModel novoUsuario) {
        LoginModel novoLogin = LoginModel.builder()
                .user(novoUsuario)
                .email(usuarioDTO.email())
                .senha(passwordEncoder.encode(usuarioDTO.senha()))
                .build();

        return novoLogin;
    }

    // função para verificar se todos os dados do cliente/usuario foram preenchidos
    // corretamente
    private void verificarDadosUsuarioCriado(UsuarioDTO usuarioDTO) {
        if (loginRepository.findByEmail(usuarioDTO.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já existente!");
        }
    }

    // Administrador atualiza o usuario
    public void atualizarDadosCadastraisUsuarioPorAdministrador(AtualizacaoUsuarioDTO usuarioDTO, Long id) {

        UsuarioModel userAtual = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        userAtual.setNome(usuarioDTO.nome());
        userAtual.setTelefone(usuarioDTO.telefone());
        userAtual.setUnidade(usuarioDTO.unidade());
        userAtual.setRole(verificaRoleUser(usuarioDTO.roleUser(), userAtual.getRole()));
        usuarioRepository.save(userAtual);

        if (usuarioDTO.usuarioComLogin()) {
            LoginModel loginAtual = loginRepository.findByEmail(usuarioDTO.email())
                    .orElseThrow(() -> new RuntimeException("E-mail não encontrado"));

            verificarDadosUsuarioAtualizado(usuarioDTO, id);

            loginAtual.setEmail(usuarioDTO.email());
            String novaSenha = passwordEncoder.encode(usuarioDTO.senha());
            if (!passwordEncoder.matches(usuarioDTO.senha(), loginAtual.getSenha()) && !usuarioDTO.senha().isEmpty()) {
                loginAtual.setSenha(novaSenha);
            }
            loginRepository.save(loginAtual);
        }

    }

    private void verificarDadosUsuarioAtualizado(AtualizacaoUsuarioDTO usuarioDTO, Long id) {
        LoginModel usuario = loginRepository.findByEmail(usuarioDTO.email())
                .orElseThrow(() -> new RuntimeException("Usuário inválido"));

        if (loginRepository.findByEmail(usuarioDTO.email()).isPresent() && !usuario.getUser().getId().equals(id)) {
            throw new IllegalArgumentException("E-mail já existente!");
        }

    }

    // Atualiza o propio usuario, função de usuario
    public void atualizarUsuario(UsuarioDTO usuarioDTO, String authorization) {
        UsuarioModel userAtual = usuarioRepository.findById(jwtTokenService.pegarId(authorization))
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

    private RoleUserModel verificaRoleUser(RoleEnum novaRole, RoleUserModel antigaRole) {
        RoleUserModel atualizarRole = verificaRole(novaRole);
        if (!antigaRole.getRole().equals(atualizarRole.getRole())) {
            RoleUserModel role = roleUserRepository.findById(antigaRole.getId())
                    .orElseThrow(() -> new RuntimeException("NÂO ERA PRO CE VE ISSO MEU BOM"));
            role.setRole(atualizarRole.getRole());
            roleUserRepository.save(role);
            return role;
        }
        return antigaRole;
    }

}
