package com.uday.vijayadairy.service;

import java.util.List;
import java.util.Random;

import org.apache.catalina.startup.FailedContext;
import org.hibernate.annotations.Check;
import org.hibernate.sql.Update;
import org.springframework.aot.hint.annotation.ReflectiveRuntimeHintsRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uday.vijayadairy.configuration.JWTService;
import com.uday.vijayadairy.model.User;
import com.uday.vijayadairy.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class Userservice {
	@Autowired
	UserRepository repository;
	@Autowired
	Emailservice emailservice;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationmanager;
	
	@Autowired
	JWTService jwtService;
	
	private String generateotp()
	{
		    Random random=new Random();
		    int num = random.nextInt(10000);
		    String otp= String.format("%04d", num);
		    return otp;
	}
	
public	String  sendotp(User user)
	{
	
		  	String otp=this.generateotp();
		    String message="you four digit otp register / forgotpasssowrd dont share with any one : "+otp;
		    emailservice.sendOtp(user.getEmail(), message, "Messiah dairy user registration otp");
		    return otp;
	}
		
public String checkuser(User user)
{
	if (user.getConfirmpassword().equals(user.getPassword()) && user.getEmail() != null) {
		User checkuserexists = repository.findByEmail(user.getEmail()).orElse(null);
		if (checkuserexists != null) {
			
			return "User Already exists";
		} 
	}
		
	 if (!user.getConfirmpassword().equals(user.getPassword())) {
			return "Passwords incorrect";
		}
	 if (user.getPassword() == null || user.getConfirmpassword() == null) {
	        return "Password should not be empty";
	    }
	 if (user == null) {
	        return "Invalid user data";
	    }
	return "new user ";
}
	public String registerUser(User user) {
		
		if (user.getConfirmpassword().equals(user.getPassword()) && user.getEmail() != null) {
			User checkuserexists = repository.findByEmail(user.getEmail()).orElse(null);
			if (checkuserexists != null) {
				
				return "User Already exists";
			} else {
				
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user = repository.save(user);
				return "user with email" + user.getEmail() + " " + "password :" + user.getPassword()
						+ " registered successfully ;";
			}
		} else if (!user.getConfirmpassword().equals(user.getPassword())) {
			return "Passwords incorrect";
		}
		return "Email should not have spaces and should be valid";
	}
	
	
	public String login(User user) {

	    authenticationmanager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    user.getEmail(),
	                    user.getPassword()
	            )
	    );

	    return jwtService.generateToken(user.getEmail());
	}
	
	
 public List<User> getallusers()
 {
	 return repository.findAll();
 }
 @Transactional
 public boolean updatepassword(User user)
 {
	 
	User user1= repository.findByEmail(user.getEmail()).orElse(null);
	if(user.getEmail().equals(user1.getEmail()))
	{
		user1.setPassword(passwordEncoder.encode(user.getPassword()));
	
		User  updateduser=repository.save(user1);
		System.out.println("updated user: "+updateduser);
		return true;
	}
	System.out.println("failed to insert");
	return false;
	 
 }
 
 public User getUserByemail(String email)
 {
	 return repository.findByEmail(email).orElse(null);
 }
 
 
}