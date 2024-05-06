package com.example.demo;

import com.example.demo.dto.Election;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

@SpringBootApplication
public class EvotingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvotingApplication.class, args);
	}

	@Bean
	public HashMap<Integer, Election> createElectionDirectory() {
		return new HashMap<>();
	}

	@Bean
	public HashMap<Integer,Object> createResultsDirectory() {
		return new HashMap<>();
	}
}
