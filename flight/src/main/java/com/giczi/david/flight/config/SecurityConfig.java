package com.giczi.david.flight.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.giczi.david.flight.service.EncoderService;
import com.giczi.david.flight.service.PassengerServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	 @Bean
	  public UserDetailsService userDetailsService() {
		 return new PassengerServiceImpl();
	 }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/console/**").permitAll()
				.antMatchers("/flight-js/**", "/flight-css/**").permitAll()
				.antMatchers("/flight/registration").permitAll()
				.antMatchers("/flight/reg").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/flight/order", true)
				.permitAll()
			.and()
			.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/login?logout")
			.permitAll();
		
		http.headers().frameOptions().disable();
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     
		auth.userDetailsService(userDetailsService()).passwordEncoder(EncoderService.passwordEncoder());
       
    }
	

}
