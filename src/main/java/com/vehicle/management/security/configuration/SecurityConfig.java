package com.vehicle.management.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
	
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder  ) {
		UserDetails admin=User.withUsername("Haresh")
				.password(encoder.encode("1234"))
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(admin);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/api/addVehicle").permitAll()
		.and()
		.authorizeHttpRequests().requestMatchers("/api/**")
		.authenticated()
		.anyRequest().authenticated()
		.and()
		
		//.authorizeHttpRequests().requestMatchers("/swagger-ui.html/**").authenticated()
		//.anyRequest().authenticated()
		//.anyRequest().authenticated()
		//.authorizeHttpRequests().requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/swagger-resources/**").permitAll()
		//.and()
		.httpBasic()
		.and().build();
		
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	

}
