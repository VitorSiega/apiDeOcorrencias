package com.example.aplicativo.login.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.aplicativo.erroStatus.ErrorResponse;
import com.example.aplicativo.login.dto.LoginDto;
import com.example.aplicativo.login.model.LoginModel;
import com.example.aplicativo.login.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login") // refatorado
    public ResponseEntity<?> loginUsuario(@RequestBody LoginDto loginUserDto) {
        try {
            return new ResponseEntity<>(loginService.autenticarUsuario(loginUserDto), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ErrorResponse("Credenciais inválidas"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar/login")
    public ResponseEntity<List<LoginModel>> listarTodosUsuario() {
        return ResponseEntity.status(200).body(loginService.listarLogin());
    }

}
