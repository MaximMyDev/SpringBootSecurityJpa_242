package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
/*
@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import(Application.class)

 */
public class Application {
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

		/*
		* SpringApplication app = new SpringApplication(CustomApplication.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "8083"));
        app.run(args);*/
	}

}
