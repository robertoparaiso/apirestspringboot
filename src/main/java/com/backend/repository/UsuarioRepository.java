package com.backend.repository;

import com.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByLoginAndSenha(String login, String senha);
    Optional<Usuario> findByLogin(String login);
}
