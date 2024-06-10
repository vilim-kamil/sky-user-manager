package com.sky.usermanager.rest.security;

import com.sky.usermanager.models.common.UserRole;
import com.sky.usermanager.services.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * Basic security configuration.
 * Currently, JDBC authentication is used in conjunction with in-memory authentication,
 * with the latter only used in case default admin account is requested.
 * <p>
 * All passwords are BCrypt encrypted by design.
 * <p>
 * Note: Only basic auth is supported, and since the API is stateless, csrf has been disabled.
 */
@Configuration
public class SecurityConfig {

    private final ConfigService configService;

    @Autowired
    public SecurityConfig(ConfigService configService) {
        this.configService = configService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(customizer ->
                customizer
                        .requestMatchers(antMatcher("/admin/**")).hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers(antMatcher("/admin/**/**")).hasAuthority(UserRole.ADMIN.name())
                        .anyRequest().authenticated()
        );

        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http, DataSource dataSource) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.parentAuthenticationManager(null);

        if (configService.getAdminUser() != null && !configService.getAdminUser().isEmpty()) {
            auth.inMemoryAuthentication()
                    .withUser(configService.getAdminUser())
                    .password(new BCryptPasswordEncoder().encode(configService.getAdminPassword()))
                    .authorities(UserRole.ADMIN.name());
        }

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());

        return auth.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
