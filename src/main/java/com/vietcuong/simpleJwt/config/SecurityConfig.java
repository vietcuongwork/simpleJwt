package com.vietcuong.simpleJwt.config;

import com.vietcuong.simpleJwt.filter.JwtAuthenticationFilter;
import com.vietcuong.simpleJwt.service.authentication.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Instance of UserDetailsServiceImpl to provide user details service
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    // Instance of JwtAuthenticationFilter for JWT token processing
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final CustomLogoutHandler logoutHandler;

    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    // Constructor to initialize UserDetailsServiceImpl and JwtAuthenticationFilter instances
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, CustomLogoutHandler logoutHandler,
                          CustomLogoutSuccessHandler logoutSuccessHandler,
                          CustomLogoutSuccessHandler logoutSuccessHandler1) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;

        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.logoutHandler = logoutHandler;
        this.logoutSuccessHandler = logoutSuccessHandler1;
    }

    // Bean definition for SecurityFilterChain to configure security settings
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login/**", "/register/**", "/clientRegister/**").permitAll() //
                        .requestMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                        .userDetailsService(userDetailsServiceImpl)
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                // Configure session management to be stateless
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT
                // authentication filter before UsernamePasswordAuthenticationFilter
                .logout(l -> l.logoutUrl("/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler(logoutSuccessHandler)).build();
    }

    // Bean definition for PasswordEncoder to encode passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder for password encoding
    }

    // Bean definition for AuthenticationManager to manage authentication processes
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Retrieve AuthenticationManager from
        // AuthenticationConfiguration
    }
}
