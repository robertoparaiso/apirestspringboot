package com.backend;

import com.backend.entity.Pais;
import com.backend.entity.Usuario;
import com.backend.repository.PaisRepository;
import com.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ManagersystemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagersystemsApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(PaisRepository paisRepository, UsuarioRepository usuarioRepository) {
		return  args -> {
			paisRepository.deleteAll();
			usuarioRepository.deleteAll();

			Pais p1 = new Pais();
			p1.setNome("Brasil");
			p1.setSigla("BR");
			p1.setGentilico("Brasileiro");
			paisRepository.save(p1);

			Pais p2 = new Pais();
			p2.setNome("Argentina");
			p2.setSigla("AR");
			p2.setGentilico("Argentino");
			paisRepository.save(p2);

			Pais p3 = new Pais();
			p3.setNome("Alemanha");
			p3.setSigla("AL");
			p3.setGentilico("Alemão");
			paisRepository.save(p3);

			Usuario u1 = new Usuario();
			u1.setLogin("convidado");
			u1.setSenha("manager");
			u1.setNome("Usuário convidado");
			u1.setAdministrador(false);
			usuarioRepository.save(u1);

			Usuario u2 = new Usuario();
			u2.setLogin("admin");
			u2.setSenha("suporte");
			u2.setNome("Gestor");
			u2.setAdministrador(true);
			usuarioRepository.save(u2);
		};
	}

}
