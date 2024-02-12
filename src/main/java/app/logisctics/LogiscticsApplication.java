package app.logisctics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LogiscticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogiscticsApplication.class, args);
	}

}
