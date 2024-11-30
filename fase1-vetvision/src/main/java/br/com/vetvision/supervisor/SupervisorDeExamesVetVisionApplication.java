package br.com.vetvision.supervisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupervisorDeExamesVetVisionApplication {

	public static void main(String[] args) {
		System.out.println("Aplicação inicializada");
		SpringApplication.run(SupervisorDeExamesVetVisionApplication.class, args);
	}

}
