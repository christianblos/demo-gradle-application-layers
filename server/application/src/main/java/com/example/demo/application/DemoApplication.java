package com.example.demo.application;

import com.example.demo.application.config.DomainConfiguration;
import com.example.demo.application.config.AdapterConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
	DomainConfiguration.class,
	AdapterConfiguration.class
})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
