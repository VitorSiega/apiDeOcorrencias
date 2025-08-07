package com.example.aplicativo.login.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.aplicativo.login.dto.JwtTokenDto;
import com.example.aplicativo.login.dto.LoginDto;
import com.example.aplicativo.login.model.LoginModel;
import com.example.aplicativo.login.model.ModelUserDetailsImpl;
import com.example.aplicativo.login.repository.LoginRepository;

@Service
public class LoginService {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final LoginRepository loginRepository;

    public LoginService(JwtTokenService jwtTokenService, AuthenticationManager authenticationManager,
            LoginRepository loginRepository) {
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.loginRepository = loginRepository;
    }

    public JwtTokenDto autenticarUsuario(LoginDto loginUserDto) {
        // Cria o token de autenticação
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDto.email(), loginUserDto.senha());
        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
        // Gera o JWT token
        return new JwtTokenDto(jwtTokenService.generateToken(modelUserDetails));
    }

    public List<LoginModel> listarLogin() {
        return loginRepository.findAll();
    }

}
