package com.example.PcShop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PcShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcShopApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(DatabaseLoader databaseLoader) {
		return args -> {
			databaseLoader.loadSqlFileIfNeeded();
		};
	}

}
