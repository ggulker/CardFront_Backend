package com.greckapps.cardfront;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greckapps.cardfront.forms.LoginForm;
import com.greckapps.cardfront.user.*;
import com.greckapps.cardfront.utils.TokenHandler;

import ch.qos.logback.core.subst.Token;



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
