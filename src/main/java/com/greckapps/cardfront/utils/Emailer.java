package com.greckapps.cardfront.utils;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;



public class Emailer {
    static SendGrid sendgrid = new SendGrid(System.getenv("sendgrid_key"));
    static String from = "greck@greck.icu";

    public void SendMail(String to, String subject, String text){
        System.out.println(System.getenv("sendgrid_key"));
        SendGrid.Email mail = new SendGrid.Email();
        mail.setFrom(from);
        mail.addTo(to);
        mail.setSubject(subject);
        mail.setText(text);
     
        try {
            SendGrid.Response response = sendgrid.send(mail);
            System.out.println(response.getMessage());
        } catch (SendGridException sge) {
            sge.printStackTrace();
        }
    }
}
