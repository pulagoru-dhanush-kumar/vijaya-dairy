package com.uday.vijayadairy.configuration;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	MyUserDetailService service;
	@Autowired
	JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    return http
	            .csrf(csrf -> csrf.disable())

	            .authorizeHttpRequests(req -> req
	                    .requestMatchers(
	                            "/users/login",
	                            "/users/register",
	                            "/users/verifyotp",
	                            "/users/forgotpassword",
	                            "/users/resetpassword"
	                           ,"/products/all"
	                            ,"/products.html"
	                            ,"index.html","adminpanel.html"
	                    ).permitAll()

	                    .anyRequest().authenticated()
	            )

	            .sessionManagement(session ->
	                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )

	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

	            .build();
	}
	 
	 @Bean
	 public AuthenticationProvider authenticationProvider()
	 {
		 DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		 provider.setUserDetailsService(service);
		 provider.setPasswordEncoder(passwordEncoder());
		 return provider;
	 }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	  return new BCryptPasswordEncoder();
	 }
	
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
 return config.getAuthenticationManager();
}
	 
	 
	
}
