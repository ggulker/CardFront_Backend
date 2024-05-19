package com.greckapps.cardfront.user;

import org.springframework.web.bind.annotation.RestController;
import com.greckapps.cardfront.utils.EmailHandler;
import com.greckapps.cardfront.utils.TokenHandler;
import com.greckapps.cardfront.utils.Utils;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	    
	@PostMapping("/password/{uid}")
	public ResponseEntity<String> sendResetCode(@PathVariable String uid) {
		User emailUser = userService.GetByUserID(uid);

		if(emailUser != null){
			HashMap<String,String> replace = new HashMap<>();
			String code = Utils.RandomCode(8);
			replace.put("{CODE}", code);

			emailHandler.SendMail(emailUser.getEmail(),"FRGTPASS", replace);
			return new ResponseEntity<>(TokenHandler.createStandToken("CODE: " + code + " UID: " + ""),HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/password")
	public ResponseEntity<Void> ValidateCode(@RequestParam("token") String token, @RequestParam("code")String code, @RequestParam("pass") String pass) {
		String tokSub = TokenHandler.getTokenSubject(token);
		String tokCode = tokSub.substring(6, 15);
		String tokUsr = tokSub.substring(21);
		if(tokCode == code){
			int ret = userService.UpdatePass(tokUsr, pass);
			if(ret == 0)
			{
					return new ResponseEntity<>(HttpStatus.OK);
			}
			else if(ret == -1)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
			return new ResponseEntity<String>(" ", HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<String>(" ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/token")
	public ResponseEntity<Void> validateToken(@RequestParam("token") String token) {
		if(TokenHandler.ValidateToken(token))
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
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
			return new ResponseEntity<String>(" ", HttpStatus.NOT_ACCEPTABLE);
		}
		else
		{
			return new ResponseEntity<String>(" ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}