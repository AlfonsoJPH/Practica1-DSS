package com.dss.practica1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "com.dss")
@EntityScan(basePackages = "com.dss.model")  // Esta anotación es clave para detectar las entidades
@EnableJpaRepositories(basePackages = "com.dss.repo")  // Esta anotación es clave para detectar los repositorios
@SpringBootApplication
public class Practica1Application {

	public static void main(String[] args) {
		SpringApplication.run(Practica1Application.class, args);
	}

}
