package ch.letsboot.jugstalk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebAuthorizationConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)
            throws Exception {

        http.formLogin(Customizer.withDefaults());

        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/login").permitAll()
                .requestMatchers("/csrf").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/courses/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/courses/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );
        return http.build();
    }



}
