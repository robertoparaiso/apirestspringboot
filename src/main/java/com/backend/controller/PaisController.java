package com.backend.controller;

import com.backend.entity.Pais;
import com.backend.service.PaisService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pais")
@AllArgsConstructor
public class PaisController {

    private final PaisService paisService;

    //SALVAR
    @RequestMapping(value = "/salvar", params = { "token" }, method = RequestMethod.POST)
    public ResponseEntity<Pais> salvarPais(@RequestParam("token") String token, @RequestBody @Valid Pais pais) {
        return paisService.salvarPais(token, pais);
    }

    //LISTAR
    @RequestMapping(value = "/listar", params = { "token" }, method = RequestMethod.GET)
    public ResponseEntity<List<Pais>> getPaises(@RequestParam("token") String token) {
        return paisService.getPaises(token);
    }

    //PESQUISAR
    @RequestMapping(value = "/pesquisar", params = { "token", "nome" }, method = RequestMethod.GET)
    public ResponseEntity<Object> pesquisarPaises(@RequestParam("token") String token, @RequestParam("nome") String nome) {
        return paisService.pesquisarPaises(token, nome);
    }

    //EXCLUIR
    @RequestMapping(value = "/excluir", params = { "token", "id" }, method = RequestMethod.GET)
    public ResponseEntity<Boolean> removaPais(@RequestParam(value = "token") String token, @RequestParam(value = "id") Long id) {
        return paisService.removaPais(token, id);
    }
}
