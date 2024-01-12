package com.greckapps.cardfront.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.greckapps.cardfront.utils.Emailer;

@SpringBootApplication()
public class UserApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);

		Emailer mailer = new Emailer();
		mailer.SendMail("gagulker@gmail.com", "TEST", "Attempting to test email capabilities");
	}

}
