package com.greckapps.cardfront;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.greckapps.cardfront.user.User;
import com.greckapps.cardfront.user.UserRepository;
import com.greckapps.cardfront.utils.Emailer;

@SpringBootTest
class CardfrontApplicationTests {
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	void CreateNewUser() {
		User user = new User();
		user.setEmail("test.test@test.com");
		user.setUser_id("java_test");
		user.setPass_enc("pass");
	
		userRepository.save(user);
	}

	@Test
	void SendEmail(){
		Emailer mailer = new Emailer();
		mailer.SendMail("gagulker@gmail.com", "TEST", "Attempting to test email capabilities");
	}
}
