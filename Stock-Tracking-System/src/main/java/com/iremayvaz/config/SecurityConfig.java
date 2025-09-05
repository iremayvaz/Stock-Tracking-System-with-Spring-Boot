package com.iremayvaz.config;

import com.iremayvaz.model.jwt.AuthEntryPoint;
import com.iremayvaz.model.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // GÜVENLİK KURALLARI

    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String REFRESH_TOKEN = "/refreshToken";

    @Autowired
    private AuthenticationProvider authenticationProvider; // Kullanıcı doğrulaması

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // Gelen isteğin header'ını kontrol eder

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Güvenlik kuralları
        http.csrf(csrf -> csrf.disable())// csrf devre dışı, çünkü ekstra CSRF token kontrolü gereksiz
                .authorizeHttpRequests(request ->
                        request.requestMatchers(LOGIN, REGISTER, REFRESH_TOKEN)
                                .permitAll()
                                .anyRequest() // Eğer authenticated değilsen
                                .authenticated()) // Filter katmanına gireceksin!
                .exceptionHandling(e -> e.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Her istekte JWT bekler
                .authenticationProvider(authenticationProvider) // Kullanıcı doğrulama sağlayıcısını (DaoAuthenticationProvider) Security’ye tanıtıyorsun.
                .addFilterBefore(jwtAuthenticationFilter, // JWT kontrolü
                        UsernamePasswordAuthenticationFilter.class); // Gelen request’in header’ında JWT varsa doğrulanır.

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ // Girdiğimiz şifreyi hash'ler
        return new BCryptPasswordEncoder();
    }

}
