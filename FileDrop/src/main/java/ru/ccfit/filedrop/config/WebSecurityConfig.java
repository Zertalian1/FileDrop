package ru.ccfit.filedrop.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.ccfit.filedrop.service.implement.UserServiceImpl;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig{
    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/").permitAll()
                                .anyRequest().denyAll()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
