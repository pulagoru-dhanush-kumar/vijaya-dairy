//package com.uday.vijayadairy.service;
//
//import java.util.Properties;
//
//import org.springframework.stereotype.Service;
//
//import jakarta.mail.Authenticator;
//import jakarta.mail.Message;
//import jakarta.mail.PasswordAuthentication;
//import jakarta.mail.Session;
//import jakarta.mail.Transport;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//
//@Service
//public class Emailservice {	
//	 public void sendOtp(String to,String body,String subject) {
//
//	     // recipient
//	        String from = "dhanushkumar6304030341@gmail.com";
//	        String host = "smtp.gmail.com";
//
//	        Properties properties = new Properties();
//	        properties.put("mail.smtp.host", host);
//	        properties.put("mail.smtp.port", "587");
//	        properties.put("mail.smtp.auth", "true");
//	        properties.put("mail.smtp.starttls.enable", "true");
//
//	        Session session = Session.getInstance(properties, new Authenticator() {
//	            @Override
//	            protected PasswordAuthentication getPasswordAuthentication() {
//	                return new PasswordAuthentication(
//	                        "dhanushkumar6304030341@gmail.com",
//	                        "satc newa yhgp vfpp"   // use Gmail App Password
//	                );
//	            }
//	        });
//	        try {
//	            MimeMessage message = new MimeMessage(session);
//	            message.setFrom(new InternetAddress(from));
//	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//	            message.setSubject(subject);
//	            message.setText(body);
//	            Transport.send(message);
//	            System.out.println("SMS sent via Email-to-SMS gateway!");
//	           System.out.println("The email has been sended from "+to +" "+"regarding  this "+subject+" "+"alng with the body"+" "+body);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }
//}
package com.uday.vijayadairy.service;

import java.util.Hashtable;
import java.util.Properties;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.springframework.stereotype.Service;
import jakarta.mail.*;
import jakarta.mail.internet.*;

@Service
public class Emailservice {

    // Helper method to check if the Domain has a Mail Server (MX Record)
    private boolean isDomainValid(String email) {
        try {
            String domain = email.substring(email.indexOf('@') + 1);
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes(domain, new String[] { "MX" });
            return attrs.get("MX") != null;
        } catch (Exception e) {
            return false; // Domain doesn't exist or has no mail server
        }
    }

    public void sendOtp(String to, String body, String subject) {
      
        if (to == null || !to.contains("@")) {
            System.out.println("Invalid email syntax: " + to);
            return;
        }

        // 2. Verify Domain Existence (MX Record Check)
        if (!isDomainValid(to)) {
            System.out.println("Email domain does not exist or cannot receive mail: " + to);
            return;
        }

        // If valid, proceed with sending
        String from = "dhanushkumar6304030341@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "satc newa yhgp vfpp");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Email successfully sent to: " + to +"  "+body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}