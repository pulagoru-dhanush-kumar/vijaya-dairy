package com.uday.vijayadairy;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.mail.Transport;


import com.uday.vijayadairy.repository.UserRepository;
import jakarta.mail.*;
import jakarta.mail.internet.*;
@SpringBootApplication
public class VijayaDairyAppliccation 
{
	@Autowired
	UserRepository userRepository;
    public static void main( String[] args )
    {
    	System.out.println("Applcation has started from main");
    	 SpringApplication.run(VijayaDairyAppliccation.class, args);
        System.out.println("Spring Application started on port number 8080");      
    	VijayaDairyAppliccation application=new VijayaDairyAppliccation();    	     
}
    public void sendOtp() {

        String to = "dhanushvip1729@gmail.com"; // recipient
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
                return new PasswordAuthentication(
                        "dhanushkumar6304030341@gmail.com",
                        "satc newa yhgp vfpp"   // use Gmail App Password
                );
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("OTP Verification");
            message.setText("Your OTP is: 123456");

            Transport.send(message);
            System.out.println("SMS sent via Email-to-SMS gateway!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}






//SpringApplication app = new SpringApplication(VijayaDairyAppliccation.class);
//app.setWebApplicationType(WebApplicationType.NONE); // disables Tom cat
//ApplicationContext context = app.run(args);
//System.out.println("Spring application started ");