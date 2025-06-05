package com.example.desafio.core.integration.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SenhaUtils {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String criptografar(String senha) {
        return passwordEncoder.encode(senha);
    }

    public static boolean verificar(String senhaDigitada, String senhaCriptografada) {
        return passwordEncoder.matches(senhaDigitada, senhaCriptografada);
    }

}
