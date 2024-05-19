package com.greckapps.cardfront.utils;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

@Service
public class EmailHandler {
    @Autowired
    private EmailRepository emailRepository;   
    static SendGrid sendgrid = new SendGrid(System.getenv("sendgrid_key"));
    static String from = "greck@greck.icu";

    //send email to passed in address from database all email fields pulled from DB 
    public void SendMail(String to, String emailid, HashMap<String,String> additions){
        Email email = emailRepository.findByEmailId(emailid);
        SendGrid.Email mail = new SendGrid.Email();
        mail.setFrom(from);
        mail.addTo(to);
        mail.setSubject( email.getEmail_sub());
        String text = email.getEmail_text();
        for (HashMap.Entry<String, String> entry : additions.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            text = text.replace(key, value);
        }    
        mail.setText(text);
        try 
        {
            SendGrid.Response response = sendgrid.send(mail);
            System.out.println(response.getMessage());
        }
        catch (SendGridException sge) 
        {
            sge.printStackTrace();
        }
    }
}
