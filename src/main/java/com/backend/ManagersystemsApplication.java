package com.backend;

import com.backend.entity.Pais;
import com.backend.entity.Usuario;
import com.backend.repository.PaisRepository;
import com.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ManagersystemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagersystemsApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(PaisRepository paisRepository, UsuarioRepository usuarioRepository) {
		return  args -> {
			savePais(paisRepository);
			saveUsuario(usuarioRepository);
		};
	}

	public void savePais(PaisRepository paisRepository) {
		paisRepository.deleteAll();
		List<Pais> listPaises = new ArrayList<>();
		listPaises.add(new Pais(null, "Brasil", "BR", "Brasileiro"));
		listPaises.add(new Pais(null, "Argentina", "AR", "Argentino"));
		listPaises.add(new Pais(null, "Alemanha", "AL", "Alemão"));
		listPaises.forEach(p -> paisRepository.save(p));
	}

	public void saveUsuario(UsuarioRepository usuarioRepository) {
		usuarioRepository.deleteAll();
		List<Usuario> listUsuario = new ArrayList<>();
		listUsuario.add(new Usuario(null, "convidado", "manager", "Usuário convidado", false));
		listUsuario.add(new Usuario(null, "admin", "suporte", "Gestor", true));
		listUsuario.forEach(u -> usuarioRepository.save(u));
	}

}
