package ch.letsboot.jugstalk.jugstalk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebAuthorizationConfig {

    private final JwtConverter jwtConverter;

    public WebAuthorizationConfig(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)
            throws Exception {

        // Disables session management as JWT based authentication is stateless
        http.sessionManagement(s -> s.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS));

        // Act as an OAuth2 Resource Server using JWTs
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(
            jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

        http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").hasAnyRole("ADMIN", "USER")
            .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/courses/**").hasAnyRole("ADMIN", "TRAINER")
            .requestMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasAnyRole("ADMIN", "TRAINER")
            .requestMatchers(HttpMethod.DELETE,"/api/v1/courses/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        );
        return http.build();
    }
}
