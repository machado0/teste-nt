package com.machado0.teste_nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.machado0.teste-nt.*")
@ComponentScan(basePackages = { "com.machado0.teste-nt.*" })
@EntityScan("com.machado0.teste-nt.*")
public class TesteNtApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteNtApplication.class, args);
	}

}
