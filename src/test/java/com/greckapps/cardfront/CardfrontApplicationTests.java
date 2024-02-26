package com.greckapps.cardfront;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.greckapps.cardfront.user.UserApplication;
import com.greckapps.cardfront.utils.TokenHandler;

import io.jsonwebtoken.Claims;


@SpringBootTest(classes = UserApplication.class)
class CardfrontApplicationTests {
	@Test
	void CreateToken()
	{
		String token = TokenHandler.createStandToken("usernameTest");
		Claims returned = TokenHandler.decodeKey(token);
		System.out.println(returned.get("sub"));
	}
}
