// package com.substring.helpdesk.configsec;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//
//// Make application Stateless
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	// Dynamic way to fetch user details from database
//	@SuppressWarnings("deprecation")
//	@Bean
//	public AuthenticationProvider authProvider() {
//
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//
//		provider.setUserDetailsService(userDetailsService);// set user details
//
//		// Authenticate
//		provider.setPasswordEncoder(new BCryptPasswordEncoder(BCryptVersion.$2Y));
//
//		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());// set password encoder here and remove NoOpPasswordEncoder                                                        //when use PasswordEncoder in future
//		return provider;
//
//	}
//
//	  // This is lambda way
//	  @Bean
//	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//     http
//        .csrf(customizer-> customizer.disable())
//	    .authorizeHttpRequests(request->request.anyRequest().authenticated()) // Enabling every request authenticated
//	    .httpBasic(Customizer.withDefaults()) // Make This Line Stateless
//	    .sessionManagement(session->
//	              session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//
//	  }
	
   // Static User Details
	  
	/*
	 * @Bean 
	 * public UserDetailsService userdetailsService() { // No User in
	 * production only learning purpose // here Multiple user creates
	 * 
	 * @SuppressWarnings("deprecation") UserDetails user = User
	 * .withDefaultPasswordEncoder() .username("shankar") .password("Sh@123")
	 * .roles("ADMIN","USER") .build();
	 * 
	 * 
	 * @SuppressWarnings("deprecation") UserDetails admin = User
	 * .withDefaultPasswordEncoder() .username("Admin") .password("Admin@123")
	 * .roles("ADMIN") .build();
	 * 
	 * 
	 * return new InMemoryUserDetailsManager(user,admin);
	 * 
	 * }
	 * 

}
 */

