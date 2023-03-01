package com.Microservice.ModulosPagosUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ModulosPagosUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModulosPagosUserApplication.class, args);
	}
/*
	@Bean
	public CommandLineRunner initData(UserRepository userRepository) {
		return (args) -> {

			User user1 = new User("Juan", "Perez", "20876444", "juan.perez@gmail.com", "12345");
			userRepository.save(user1);
			User user2 = new User("Camila", "Rodriguez", "27345672","camila@rodriguez.com","12345");
			userRepository.save(user2);
			User user3 = new User("Lucas", "Sanchez", "25786453","lucas@sanchez.com","12345");
			userRepository.save(user3);
		};
	}
	*/
}
