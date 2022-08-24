package com.backend.service;

import com.backend.entity.Pais;
import com.backend.entity.Token;
import com.backend.repository.PaisRepository;
import com.backend.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaisService {

    private final PaisRepository paisRepository;
    private final TokenRepository tokenRepository;

    public ResponseEntity<Pais> salvarPais(String token, Pais pais) {
        //VALIDA TOKEN
        if (!validaToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Pais());
        }

        //SALVA PAIS CASO ID=0 OU ID INFORMADO NAO EXISTE
        Optional<Pais> paisOptional = paisRepository.findById(pais.getId());
        if (pais.getId() == 0 || !paisOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.paisRepository.save(pais));
        }

        //CASO NAO SATISFACA NENHUMA DAS CONDICOES ATUALIZA
        return ResponseEntity.status(HttpStatus.OK).body(atualizaPais(pais));
    }

    public Pais atualizaPais(Pais pais) {
        Optional<Pais> paisOptional = paisRepository.findById(pais.getId());
        Pais paisUpdate = paisOptional.get();
        paisUpdate.setNome(pais.getNome());
        paisUpdate.setSigla(pais.getSigla());
        paisUpdate.setGentilico(pais.getGentilico());

        return this.paisRepository.save(paisUpdate);
    }

    public ResponseEntity<List<Pais>> getPaises(String token) {
        //VALIDA TOKEN
        if (!validaToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<>());
        }

        //LISTA TODOS PAISES
        return ResponseEntity.status(HttpStatus.OK).body(paisRepository.findAll());
    }

    public ResponseEntity<Object> pesquisarPaises(String token, String nome) {
        //VALIDA TOKEN
        if (!validaToken(token)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token invalido ou expirado.");
        }

        //CONSULTA SE NOME INFORMADO EXISTE
        Optional<Pais> paisOptional = paisRepository.findByNome(nome);
        if (!paisOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pais não localizado.");
        }

        //LISTA PAIS INFORMADO
        return ResponseEntity.status(HttpStatus.OK).body(paisOptional.get());
    }

    public ResponseEntity<Boolean> removaPais(String token, Long id) {
        //VALIDA TOKEN
        if (!validaToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Boolean.FALSE);
        }

        //VARIFICA SE ID INFORMADO EXISTE
        Optional<Pais> paisOptional = paisRepository.findById(id);
        if (!paisOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Boolean.FALSE);
        }

        //DELETA PAIS
        paisRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }

    public Boolean validaToken(String token) {
        //VERIFICA SE TOKEN EXISTE
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if (!tokenOptional.isPresent()) {
            return Boolean.FALSE;
        }

        //VERIFICA SE É ADIMINISTRADOR OU SE DATA EXPIROU
        LocalDateTime dataExpiracao = tokenOptional.get().getExpiracao();
        if (!tokenOptional.get().getAdministrador() || LocalDateTime.now().isAfter(dataExpiracao)) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
