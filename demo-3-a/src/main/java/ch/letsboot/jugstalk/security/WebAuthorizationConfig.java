package ch.letsboot.jugstalk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;

@Configuration
public class WebAuthorizationConfig {

   //private final JwtConverter jwtConverter;

   //public WebAuthorizationConfig(JwtConverter jwtConverter) {this.jwtConverter = jwtConverter; }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        //  Parse the JWT, extract the necessary information (like user details or authorities), and create an authentication object.

        // Alternative 1: Use a JwtDecoder
        // http.oauth2ResourceServer(oauth2 -> oauth2.jwt(
        //        jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

        // Alternative 2: Use the JwtAuthenticationConverter()
        http.oauth2ResourceServer(oauth2Configurer -> oauth2Configurer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");
            var grantedAuthorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();
            return new JwtAuthenticationToken(jwt, grantedAuthorities);
        })));

        http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").hasAnyRole("ADMIN", "USER")
            .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/courses/**").hasAnyRole("ADMIN", "TRAINER")
            .requestMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasAnyRole("ADMIN", "TRAINER")
            .requestMatchers(HttpMethod.DELETE,"/api/v1/courses/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET,"/*").permitAll()
            .anyRequest().authenticated()
        );
        return http.build();
    }
}
