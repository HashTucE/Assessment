package com.mediscreen.assessment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen")
public class AssessmentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

	@Override
	public void run(String... args) {

		System.out.println(" ");
		System.out.println("                                                          ");
		System.out.println("     /\\                                               _   ");
		System.out.println("    /  \\   ___ ___  ___  ___ ___ _ __ ___   ___ _ __ | |_ ");
		System.out.println("   / /\\ \\ / __/ __|/ _ \\/ __/ __| '_ ` _ \\ / _ \\ '_ \\| __|");
		System.out.println("  / ____ \\\\__ \\__ \\  __/\\__ \\__ \\ | | | | |  __/ | | | |_ ");
		System.out.println(" /_/    \\_\\___/___/\\___||___/___/_| |_| |_|\\___|_| |_|\\__|");
		System.out.println(" =========================================================");
		System.out.println(" :: APP ::                                        (v1.0.0)");
		System.out.println(" ");
	}
}
