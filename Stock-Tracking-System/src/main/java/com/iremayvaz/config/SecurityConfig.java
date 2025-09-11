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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // spring Security aktif
@RequiredArgsConstructor
public class SecurityConfig {
    // GÜVENLİK KURALLARI

    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String REFRESH_TOKEN = "/refresh-token";

    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Gelen isteğin header'ını kontrol eder
    private final UserDetailsService userDetailsService; // AppUserDetailsService gelecek Spring Context'ten
    private final AuthEntryPoint authEntryPoint; // unauthenticated işlemler için 401 (UNAUTHORIZED) hataları üretir

    @Bean
    public PasswordEncoder passwordEncoder(){ // Girdiğimiz şifreyi hash'ler
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){ // SecurityFilterChain için
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(); // DB'den kullanıcı doğrulaması yapar
        daoAuthenticationProvider.setUserDetailsService(userDetailsService); // Kullanıcıların nasıl yükleneceğini bildirir
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Şifrelerin nasıl karşılaştırılacağını söyler

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Spring Security'nin ana konfigürasyonu
        http.csrf(csrf -> csrf.disable())// csrf devre dışı, çünkü ekstra CSRF token kontrolü gereksiz
                .authorizeHttpRequests(request ->
                        request.requestMatchers(LOGIN, REGISTER, REFRESH_TOKEN)
                                .permitAll() // bu endpoint'lere herkes erişebilir
                                .anyRequest() // Eğer authenticated değilsen
                                .authenticated()) // Filter katmanına gireceksin! Authenticate olmalısın!
                .exceptionHandling(e -> e.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Her istekte JWT bekler????????????????????????????????????????????????????
                .authenticationProvider(authenticationProvider()) // Kullanıcı doğrulama sağlayıcısını (DaoAuthenticationProvider) Security’ye tanıtıyorsun.
                .addFilterBefore(jwtAuthenticationFilter, // JWT kontrolü
                        UsernamePasswordAuthenticationFilter.class); // Gelen request’in header’ında JWT varsa doğrulanır.

        return http.build();
    }

    // Cross-Site Request Forgery : Siteler arası istek sahteciliği,
    // tarayıcıda banka oturumu açtın sonra yeni sekmede başka siteye girdin.
    // O site senmiş gibi bankaya istek atabilir.
}
