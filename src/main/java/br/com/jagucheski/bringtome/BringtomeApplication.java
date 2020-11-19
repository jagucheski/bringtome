package br.com.jagucheski.bringtome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BringtomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BringtomeApplication.class, args);
	}

}
