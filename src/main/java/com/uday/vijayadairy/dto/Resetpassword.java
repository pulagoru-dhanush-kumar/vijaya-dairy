package com.uday.vijayadairy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Resetpassword {
	@NotBlank(message="email is required")
	@Email(message = "Invalild email format")
 String email;
	 @NotBlank(message = "password is required")
 String password;
	 @NotBlank(message = "confirm password is required")
 String confirmpassword;
}
