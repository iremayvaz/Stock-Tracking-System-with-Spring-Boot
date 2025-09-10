package com.iremayvaz.model.userDetails;

import com.iremayvaz.model.entity.User;
import com.iremayvaz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // UserDetailsService bean'i olarak kaydedilecek. Ve her UserDetailsService @AutoWired edildiğinde bu enjekte edilecek!
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    // UserDetailsService : Spring Security'nin kullanıcı yükleme interface'i

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Geçersiz email : " + email));

        System.out.println("role = " + user.getRole().getName());
        System.out.println("perms = " + user.getRole().getPermissions()); // burada dolu mu?

        return AppUserDetails.from(user);
    }
}
