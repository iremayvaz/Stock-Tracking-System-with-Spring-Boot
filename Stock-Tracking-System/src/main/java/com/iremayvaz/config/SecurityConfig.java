package com.iremayvaz.config;

import com.iremayvaz.model.jwt.AuthEntryPoint;
import com.iremayvaz.model.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    // GÜVENLİK KURALLARI

    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String REFRESH_TOKEN = "/refreshToken";

    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Gelen isteğin header'ını kontrol eder
    private final UserDetailsService userDetailsService; // AppUserDetailsService gelecek context'ten
    private final AuthEntryPoint authEntryPoint; // unauthenticated işlemler için 401 hataları üretir

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ // Girdiğimiz şifreyi hash'ler
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

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
                .authenticationProvider(authenticationProvider()) // Kullanıcı doğrulama sağlayıcısını (DaoAuthenticationProvider) Security’ye tanıtıyorsun.
                .addFilterBefore(jwtAuthenticationFilter, // JWT kontrolü
                        UsernamePasswordAuthenticationFilter.class); // Gelen request’in header’ında JWT varsa doğrulanır.

        return http.build();
    }



}
