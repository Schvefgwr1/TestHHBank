package bank.testhhbank;

import jakarta.validation.Validation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestHhBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestHhBankApplication.class, args);
	}

}
