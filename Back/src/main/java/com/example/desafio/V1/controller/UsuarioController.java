package com.example.desafio.V1.controller;

import com.example.desafio.V1.dto.UsuarioDTO;
import com.example.desafio.core.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@Valid @RequestBody UsuarioDTO dto) {
        String resultado = usuarioService.registrarUsuario(dto);
        if (resultado == null) {
            return ResponseEntity.internalServerError().body("Erro interno: resultado nulo.");
        }

        if (resultado.startsWith("Erro")) {
            return ResponseEntity.badRequest().body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

}
