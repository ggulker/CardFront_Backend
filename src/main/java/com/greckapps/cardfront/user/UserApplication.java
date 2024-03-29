package com.greckapps.cardfront.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@ComponentScan(basePackages = {"com.greckapps.cardfront.utils","com.greckapps.cardfront.user"})
@EntityScan(basePackages = {"com.greckapps.cardfront.utils","com.greckapps.cardfront.user"})
@EnableJpaRepositories(basePackages = {"com.greckapps.cardfront.utils","com.greckapps.cardfront.user"})
public class UserApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
