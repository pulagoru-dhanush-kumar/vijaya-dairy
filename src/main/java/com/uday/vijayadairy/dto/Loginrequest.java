package com.uday.vijayadairy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class Loginrequest {
	
	@NotBlank(message="email is required")
	@Email(message = "Invalild email format")
	String email;
	 @NotBlank(message = "password is required")
	String password;
}
