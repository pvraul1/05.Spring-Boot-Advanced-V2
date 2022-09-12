package com.in28minutes.springboot.firstrestapi.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The spring security configuration class.
 *
 * @author architecture - pvraul1
 * @since 1.17
 * @version Aug 29, 2022 - 5:13:29 PM
 *
 */
@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {

        UserDetails userDetails1 = createNewUser("admin", "password");
        UserDetails userDetails2 = createNewUser("sadasiva", "dummydummy");

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    private UserDetails createNewUser(String username, String password) {
        Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);

        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();

        return userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated());
        http.httpBasic(withDefaults());

        http.csrf().disable(); // POST or PUT
        http.headers().frameOptions().disable();

        return http.build();
    }

}
