package com.profilerenovation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.profilerenovation.entity.User;
import com.profilerenovation.enums.Role;
import com.profilerenovation.repository.UserRepository;

@SpringBootApplication
@ComponentScan(basePackages = { "com.profilerenovation" })
public class BackendProfileRenovationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendProfileRenovationApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Verificar si el usuario ya existe
			if (!userRepository.existsByEmail("taras@gmail.com")) {
				// Crear un nuevo usuario con el rol más alto
				User user = new User();
				user.setUserName("taras");
				user.setEmail("taras@gmail.com");
				user.setPassword(passwordEncoder.encode("1234"));
				user.setRole(Role.ADMIN_PADRE);
				userRepository.save(user);
			}
		};
	}

}
