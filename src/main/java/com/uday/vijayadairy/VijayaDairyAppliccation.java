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
     
}






//SpringApplication app = new SpringApplication(VijayaDairyAppliccation.class);
//app.setWebApplicationType(WebApplicationType.NONE); // disables Tom cat
//ApplicationContext context = app.run(args);
//System.out.println("Spring application started ");