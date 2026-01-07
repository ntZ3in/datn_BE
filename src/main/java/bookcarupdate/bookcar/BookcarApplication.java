package bookcarupdate.bookcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookcarApplication {

	public static void main(String[] args) {

		SpringApplication.run(BookcarApplication.class, args);
	}

}
