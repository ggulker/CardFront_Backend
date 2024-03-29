package com.greckapps.cardfront;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.greckapps.cardfront.user.*;
import com.greckapps.cardfront.utils.TokenHandler;




@SpringBootTest(classes = UserApplication.class)
class CardfrontApplicationTests {
	@Test
	void CreateToken()
	{
		String token = TokenHandler.createStandToken("usernameTest");
		Boolean result = TokenHandler.ValidateToken(token);
		assertTrue(result);
	}

	@Test
	void getUsername()
	{
		String token = TokenHandler.createStandToken("usernameTest");
		assertTrue(TokenHandler.getTokenSubject(token).equals("usernameTest"));
	}
}
