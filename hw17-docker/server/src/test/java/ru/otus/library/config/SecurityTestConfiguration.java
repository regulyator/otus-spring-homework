package ru.otus.library.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.Set;

@TestConfiguration
public class SecurityTestConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {

        return new InMemoryUserDetailsManager(Collections.singletonList(ru.otus.library.domain.User.builder()
                .username("user")
                .password("password")
                .authorities(Set.of(new SimpleGrantedAuthority("ROLE_USER")))
                .enabled(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .build()));
    }
}
