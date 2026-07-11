package com.taskManagementSystem.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		http
			.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(HttpMethod.GET,"/api/tasks/**").permitAll()
					.requestMatchers("/h2-console/**").permitAll()
					.requestMatchers("/v3/api-docs/**").permitAll()
	                .requestMatchers("/swagger-ui/**").permitAll()
	                .requestMatchers("/swagger-ui.html").permitAll()
					.anyRequest().authenticated()
					)
			.httpBasic(Customizer.withDefaults())
			.headers(headers -> headers.frameOptions(frame -> frame.disable()));
		
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails admin = User.builder()
				.username("swaroop")
				.password("{noop}password")
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(admin);
	}
}
