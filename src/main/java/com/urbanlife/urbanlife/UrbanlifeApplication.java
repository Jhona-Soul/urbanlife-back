package com.urbanlife.urbanlife;

import com.urbanlife.urbanlife.models.request.AuthenticationRequest;
import com.urbanlife.urbanlife.models.request.RegisterRequest;
import com.urbanlife.urbanlife.repository.UserRepository;
import com.urbanlife.urbanlife.services.AuthenticationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.urbanlife.urbanlife.models.usuario.RolUser.ADMIN;

@SpringBootApplication
@OpenAPIDefinition
public class UrbanlifeApplication {
	public static void main(String[] args) {
		SpringApplication.run(UrbanlifeApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service,
			UserRepository userRepository
	) {
		return args -> {
			if (userRepository.findByEmail("admin@mail.com").isPresent()) {
				System.out.println("Existe este usuario");
				var adminLogin = AuthenticationRequest.builder()
						.email("admin@mail.com")
						.password("password")
						.build();
				System.out.println("Admin token: " + service.authenticate(adminLogin).getAccessToken());
			} else {
				var admin = RegisterRequest.builder()
						.nombre("Admin")
						.apellido("Admin")
						.email("admin@mail.com")
						.password("password")
						.telefono(2312312)
						.role(ADMIN)
						.build();
				System.out.println("Admin token: " + service.registerAdmin(admin).getAccessToken());
			}
		};
	}
}
