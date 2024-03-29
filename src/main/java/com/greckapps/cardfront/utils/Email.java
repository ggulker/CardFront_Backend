package com.greckapps.cardfront.utils;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "EMAIL_TEXT")
@Entity
public class Email {
    
    @Id
    String email_id;
    String email_text,email_sub;


    public String getEmail_id() {
        return email_id;
    }
    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getEmail_text() {
        return email_text;
    }
    public void setEmail_text(String email_text) {
        this.email_text = email_text;
    }
    
    public String getEmail_sub() {
        return email_sub;
    }
    public void setEmail_sub(String email_sub) {
        this.email_sub = email_sub;
    }

}
