package com.storeeverythin.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.storeeverythin.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public WebSecurityConfig(UserService appUserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.appUserService = appUserService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	private final UserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("register").permitAll()
                .anyRequest().authenticated()
        )
        .formLogin(formLogin ->
            formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
        )
        .logout(logout ->
            logout
                .permitAll()
        );
    return http.build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
}
