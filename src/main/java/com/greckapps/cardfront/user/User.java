package com.greckapps.cardfront.user;

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
	  private String user_id, pass_enc, email, user_uuid;
	  
	public Integer getUser_key() {
		return user_key;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getPass_enc() {
		return pass_enc;
	}
	public void setPass_enc(String pass_enc) {
		this.pass_enc = pass_enc;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setUUID(String uuid)
	{
		user_uuid = uuid;
	}
	public String getUUID() {
		return user_uuid;
	}  
}
