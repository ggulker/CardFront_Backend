package com.greckapps.cardfront.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "users")
@Entity
public class User {
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Integer user_key;
	  @Column(name = "user_id", nullable = false, unique = true)
	  private String userId;
	  @Column(name = "pass_enc", nullable = false, unique = true)
	  private String passEnc;
	  @Column(name = "email", nullable = false, unique = false)
	  private String email;
	  
	public Integer getUser_key() {
		return user_key;
	}
	
	public String getUser_id() {
		return userId;
	}
	public void setUser_id(String user_id) {
		this.userId = user_id;
	}
	
	public String getPass_enc() {
		return passEnc;
	}
	public void setPass_enc(String pass_enc) {
		this.passEnc = pass_enc;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString()
	{
		return "USERID: " + userId + "\nPASSENC: " + passEnc + "\nEmail: " + email;
	}
}
