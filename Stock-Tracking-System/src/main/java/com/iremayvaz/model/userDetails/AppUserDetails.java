package com.iremayvaz.model.userDetails;

import com.iremayvaz.model.entity.User;
import com.iremayvaz.model.entity.enums.Permission;
import com.iremayvaz.model.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class AppUserDetails implements UserDetails { // Role + permission flatten mantığı burada

    // Neden final?
    // Çünkü kimlik doğrulamasından sonra kimlik bilgileri değişmemeli!
    private final Long id;
    private final String email;
    private final String password;
    private final RoleName roleName;
    private final Set<Permission> permissions;

    public static AppUserDetails from(User user){
        return new AppUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getName(),
                user.getRole().getPermissions()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> auths = new HashSet<>();
        auths.add(new SimpleGrantedAuthority("ROLE_" + roleName.name()));

        for(Permission p : permissions){
            auths.add(new SimpleGrantedAuthority(p.name()));
        }

        System.out.println("auths = " + auths.stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        return auths;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
