package com.cts.auction.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig
{
 
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
 
    	http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests((authorize)->{
                    authorize.requestMatchers("auction/user/signup","auction/user/login","auction/user/addAmount","auction/product/**","auction/**").permitAll();
 
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    

	@Bean
	public UserAuthorityService userDetailsService()
	{ 
		return new UserAuthorityService();
	}

	@Bean
	public AuthenticationProvider authenticateProvider()
	{
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
 
}