package ch.letsboot.jugstalk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserManagementConfig {

    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {

        UserDetails admin = User.builder()
                .username("admin")
                // Use BCryptPasswordEncoder to encode the password
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN")
                .build();
        // InMemoryUserDetailsManager implements UserDetailsService to provide support
        // for username/password based authentication that is stored in memory.
        return new InMemoryUserDetailsManager(admin);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}