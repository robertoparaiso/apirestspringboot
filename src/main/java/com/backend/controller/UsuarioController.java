package com.backend.controller;

import com.backend.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    //AUTENTICAR
    @RequestMapping(value = "/autenticar", params = { "login", "senha" }, method = RequestMethod.POST)
    public ResponseEntity<Object> autentiqueUsuario(@RequestParam("login") String login, @RequestParam("senha") String senha) {
        return usuarioService.autentiqueUsuario(login, senha);
    }

    //RENOVAR
    @RequestMapping(value = "/renovar-ticket", params = { "token" }, method = RequestMethod.GET)
    public ResponseEntity<Boolean> renoveToken(@RequestParam("token") String token) {
        return usuarioService.renoveToken(token);
    }

}
