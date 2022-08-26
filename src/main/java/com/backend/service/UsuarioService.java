package com.backend.service;

import com.backend.entity.Token;
import com.backend.entity.Usuario;
import com.backend.entity.dto.UsuarioAutenticado;
import com.backend.repository.TokenRepository;
import com.backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;

    public ResponseEntity<Object> autentiqueUsuario(String login, String senha) {
        //VERIFICA SE LOGIN E SENHA ESTAM CORRETOS
        Boolean autenticado = usuarioRepository.existsByLoginAndSenha(login, senha);

        //CAPTURA DADOS DO USUARIO
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);

        String tokenGen = !autenticado ? null : UUID.randomUUID().toString();
        Boolean admin = !autenticado ? Boolean.FALSE : usuario.get().getAdministrador();
        String nome = !autenticado ? null : usuario.get().getNome();
        String loginValido = !autenticado ? null : login;

        //SALVA TOKEN CASO AUTENTICACAO VALIDA
        if (autenticado) {
            salvaToken(tokenGen, admin);
        }

        //SALVA DADOS DO USUARIOAUTENTICADO PARA RETORNAR VALORES
        UsuarioAutenticado ua = salvaUsuarioAutenticado(autenticado, loginValido, admin, tokenGen, nome);

        return ResponseEntity.status(HttpStatus.OK).body(ua);
    }

    public void salvaToken(String tokenGen, Boolean admin) {
        Token token = new Token();
        token.setToken(tokenGen);
        token.setAdministrador(admin);
        token.setExpiracao(LocalDateTime.now().plusMinutes(5));
        tokenRepository.save(token);
    }

    public UsuarioAutenticado salvaUsuarioAutenticado(Boolean autenticado,
                     String loginValido, Boolean admin, String tokenGen, String nome) {
        UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado();
        usuarioAutenticado.setAutenticado(autenticado);
        usuarioAutenticado.setLogin(loginValido);
        usuarioAutenticado.setAdministrador(admin);
        usuarioAutenticado.setToken(tokenGen);
        usuarioAutenticado.setNome(nome);

        return usuarioAutenticado;
    }

    public ResponseEntity<Boolean> renoveToken(String token) {
        //VALIDA TOKEN
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if (!tokenOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Boolean.FALSE);
        }

        //ATUALIZA DATA E SALVA
        tokenOptional.get().setExpiracao(LocalDateTime.now().plusMinutes(5));
        tokenRepository.save(tokenOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }
}
