package com.backend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAutenticado {
    private Boolean administrador;
    private Boolean autenticado;
    private String login;
    private String nome;
    private String token;
}
