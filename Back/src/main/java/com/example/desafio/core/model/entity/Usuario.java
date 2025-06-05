package com.example.desafio.core.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    private String email;

    private String senha;
}
