package com.iremayvaz.config;

import com.iremayvaz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    // UserDetails Spring Security'nin kullanıcıyı db'den bulmak için çağırdığı SPI'dır.
    public UserDetailsService userDetailsService(){ // DB'den kullanıcı çeker.
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + "Email kaydı bulunamadı!"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){ // Kullanıcı tarafından girilen ad ve şifre doğrulama
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); // DB'den kullanıcıyı bul Şifreyi karşılaştır
        authenticationProvider.setUserDetailsService(userDetailsService()); // Kullanıcıyı nasıl bulacak?
        authenticationProvider.setPasswordEncoder(passwordEncoder); // Şifreyi hangi encoder ile kontrol edecek?

        return authenticationProvider;
    }
}
