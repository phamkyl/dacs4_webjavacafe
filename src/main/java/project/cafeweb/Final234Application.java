package project.cafeweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "project.cafeweb.model")
public class Final234Application {

	public static void main(String[] args) {
		SpringApplication.run(Final234Application.class, args);
	}
	
}
