package org.goya.daw2.dwes.d3.ud3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {
		// @formatter:off
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout(LogoutConfigurer::permitAll);
		// @formatter:on

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService(PasswordEncoder encoder) {

	    String password1 = encoder.encode("password");
	    UserDetails user = User.withUsername("user")
	            .password(password1)
	            .roles("USER")
	            .build();

	    String password2 = encoder.encode("passwd");
	    UserDetails rachel = User.withUsername("rachel")
	            .password(password2)
	            .roles("USER")
	            .build();

	    String password3 = encoder.encode("1234");
	    UserDetails admin = User.withUsername("admin")
	            .password(password3)
	            .roles("ADMIN", "USER")   
	            .build();

	    return new InMemoryUserDetailsManager(user, rachel, admin);
	}

}