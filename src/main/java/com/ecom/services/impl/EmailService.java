package com.ecom.services.impl;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    public  boolean  sendEmail(String to, String subject, String message)
    {
        boolean f=false;

        String from = "akanjantest@gmail.com";

        //Variable for Gmail
        String host="smtp.gmail.com";


        //get the system properties
        Properties properties = System.getProperties();
        //System.out.println("PROPERTIES"+properties);

        //Setting important information to properties object

        //host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        //properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");



        //Step 1: to get the session object....
        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("akanjantest@gmail.com", "xoctjtlclglaogbq");
            }

        });



        session.setDebug(true);


        //Step 2 : Compose the message[Text, multi media]

        MimeMessage m = new MimeMessage(session);

        try {
            //form email
            m.setFrom(from);

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);

            //adding text to message
            //m.setText(message);
            m.setContent(message,"text/html");

            //Send

            //Step 3 : send the message using transport class
            Transport.send(m);


            System.out.println("Send successfully......");


            f=true;

        } catch (MessagingException e) {

            e.printStackTrace();
        }
        return f;
    }
}
