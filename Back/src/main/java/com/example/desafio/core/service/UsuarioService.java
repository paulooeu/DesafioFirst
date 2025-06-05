package com.example.desafio.core.service;

import com.example.desafio.V1.dto.UsuarioDTO;
import com.example.desafio.core.integration.repository.UsuarioRepository;
import com.example.desafio.core.integration.util.SenhaUtils;
import com.example.desafio.core.model.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;




    public String registrarUsuario(UsuarioDTO dto) {
        if (!dto.getSenha().equals(dto.getConfirmarSenha())) {
            return "Erro: Senhas não coincidem.";
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            return "Erro: E-mail já está em uso.";
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        usuario.setSenha(SenhaUtils.criptografar(dto.getSenha()));

        usuarioRepository.save(usuario);
        return "Usuário registrado com sucesso.";
    }
}
