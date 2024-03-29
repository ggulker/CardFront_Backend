package com.greckapps.cardfront.user;

import org.springframework.web.bind.annotation.RestController;
import com.greckapps.cardfront.forms.LoginForm;
import com.greckapps.cardfront.forms.RegisterForm;
import com.greckapps.cardfront.utils.EmailHandler;
import com.greckapps.cardfront.utils.TokenHandler;
import com.greckapps.cardfront.utils.Utils;

import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;



@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
	@Autowired
	EmailHandler emailHandler;
	    
	@PutMapping("/password/{uid}")
	public ResponseEntity<String> resetUser(@PathVariable String uid) {
		System.out.println(uid);
		User emailUser = userRepository.findByUsername(uid);

		if(emailUser != null){
			HashMap<String,String> replace = new HashMap<>();
			String code = Utils.RandomCode(8);
			replace.put("{CODE}", code);

			emailHandler.SendMail(emailUser.getEmail(),"FRGTPASS", replace);
			return new ResponseEntity<>(TokenHandler.createStandToken(code),HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<String> AuthUser(@RequestBody LoginForm loginForm ) {
		User foundUser = userRepository.findByUsername(loginForm.getUsername());
		if(foundUser != null)
		{
			if(loginForm.getPassword().equals(foundUser.getPass_enc())){
				
				return new ResponseEntity<String>(TokenHandler.createStandToken(loginForm.getUsername()), HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>(" ", HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<String>(" ", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/token")
	public ResponseEntity<Void> postMethodName(@RequestBody String token) {
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
	public ResponseEntity<String> createUser(@RequestBody RegisterForm registerForm) {
		if(userRepository.findByUsername(registerForm.getUsername()) != null)
		{
			return new ResponseEntity<>("", HttpStatus.ALREADY_REPORTED);
		}

		User newUser = new User();

		newUser.setUser_id(registerForm.getUsername());
		newUser.setPass_enc(registerForm.getPassword());
		newUser.setEmail(registerForm.getEmail());
		newUser.setUUID(DigestUtils.sha256Hex(registerForm.getUsername()+registerForm.getEmail()));
		userRepository.save(newUser);

		return new ResponseEntity<>(TokenHandler.createStandToken(registerForm.getUsername()), HttpStatus.OK);
	}
}