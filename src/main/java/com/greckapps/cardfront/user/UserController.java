package com.greckapps.cardfront.user;

import org.springframework.web.bind.annotation.RestController;

import com.greckapps.cardfront.user.error.UserError;
import com.greckapps.cardfront.user.error.UserErrorResponse;
import com.greckapps.cardfront.utils.EmailHandler;
import com.greckapps.cardfront.utils.TokenHandler;
import com.greckapps.cardfront.utils.Utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;



@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {
	private EmailHandler emailHandler;
	private UserService userService;
	public UserController(UserService userService, EmailHandler emailHandler){
		this.emailHandler = emailHandler;
		this.userService = userService;
	}

	    
	@GetMapping("/password/{uid}")
	public String sendResetCode(@PathVariable String uid){
		User emailUser = userService.GetByUserID(uid);
		if(emailUser != null){
			HashMap<String,String> replace = new HashMap<>();
			String code = Utils.RandomCode(8);
			replace.put("{CODE}", code);
			emailHandler.SendMail(emailUser.getEmail(),"FRGTPASS", replace);
			return TokenHandler.createStandToken("CODE: " + code + " UID: " + uid);
		}
		else
			throw new UserError.NoUser("User ID returned no user from DB");
	}
	
	@PutMapping("/password")
	public ResponseEntity<Void> ValidateCode(@RequestParam("token") String token, @RequestParam("code")String code, @RequestParam("pass") String pass) {
		String tokSub = TokenHandler.getTokenSubject(token);
		String tokCode = tokSub.substring(6, 14);
		String tokUsr = tokSub.substring(20);

		if(tokCode.equals(code) && TokenHandler.ValidateToken(token)){
			int ret = userService.UpdatePass(tokUsr, pass);
			if(ret == 0)
			{
					return new ResponseEntity<>(HttpStatus.OK);
			}
			else if(ret == -1)
			{
				throw new UserError.NoUser("User ID returned no user from DB");
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
		else
			throw new UserError.InvalidCode("Code sent was invalid");
	}
	

	@PostMapping
	public ResponseEntity<String> AuthUser(@ModelAttribute User user) {
		int ret = userService.ValidateUser(user);
		if(ret == 0)
		{
				return new ResponseEntity<String>(TokenHandler.createStandToken(user.getUser_id()), HttpStatus.OK);
		}
		else if(ret == -1)
		{
			throw new UserError.NoUser("User ID returned no user from DB");
		}
		else
		{
			return new ResponseEntity<String>(" ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/token")
	public ResponseEntity<Void> ValidateToken(@RequestParam("token") String token) {
		if(TokenHandler.ValidateToken(token))
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
		{
			throw new UserError.InvalidCode("Token sent expired\\invalid");
		}
	}
	

	@PutMapping
	public ResponseEntity<String> createUser(@ModelAttribute User user) {
		int ret = userService.AddUser(user);
		if(ret == 0)
		{
			return new ResponseEntity<String>(TokenHandler.createStandToken(user.getUser_id()), HttpStatus.OK); 
		}
		else if(ret == -1)
		{
			throw new UserError.FoundUser("User is already present in DB");
		}
		else
		{
			return new ResponseEntity<String>(" ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler
	public ResponseEntity<UserErrorResponse> UserErrorResponse(UserError error)
	{
		UserErrorResponse response = new UserErrorResponse();
		response.setMessage(error.getMessage());
		response.setTimestamp(LocalDateTime.now());
		
		switch (error.getId()) {
			case "UFE":
				response.setStatus(HttpStatus.ALREADY_REPORTED);
				return new ResponseEntity<>(response,HttpStatus.ALREADY_REPORTED);

			case "UNFE", "IC":
				response.setStatus(HttpStatus.NOT_FOUND);
				return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}

		response.setStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST); 
	}
}