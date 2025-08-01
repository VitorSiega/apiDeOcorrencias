package com.example.aplicativo.usuario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aplicativo.erroStatus.ErrorResponse;
import com.example.aplicativo.usuario.dto.UsuarioDTO;
import com.example.aplicativo.usuario.model.UsuarioModel;
import com.example.aplicativo.usuario.service.UsuarioService;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro/usuario")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.criarUsuario(usuarioDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // opção do usuario atualizar seu propio perfil
    @PutMapping("/atualizar/usuario")
    public ResponseEntity<?> atualizarUsuarioAtual(@RequestBody UsuarioDTO usuarioDTO,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            usuarioService.atualizarUsuarioAtual(usuarioDTO, authorizationHeader);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // opção do administrador atualizar usuario
    @PutMapping("/atualizar/administrador/usuario/{id}")
    public ResponseEntity<?> administradorAtualizarUsuario(@RequestBody UsuarioDTO usuarioDTO,
            @RequestHeader("Authorization") String authorizationHeader, @RequestParam Long id) {
        try {
            usuarioService.atualizarUsuario(usuarioDTO, id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletar/usuario/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.deletarUsuario(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar/usuario/{id}")
    public ResponseEntity<?> ListarUsuario(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioService.buscarUsuario(id));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar/usuario")
    public ResponseEntity<List<UsuarioModel>> listarTodosUsuario() {
        return ResponseEntity.status(201).body(usuarioService.listUser());
    }

}
