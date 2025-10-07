package com.iremayvaz.services.impl;

import com.iremayvaz.model.dto.DtoUser;
import com.iremayvaz.model.dto.DtoUserInsert;
import com.iremayvaz.model.entity.Employee;
import com.iremayvaz.model.entity.RefreshToken;
import com.iremayvaz.model.entity.Role;
import com.iremayvaz.model.entity.User;
import com.iremayvaz.model.jwt.AuthRequest;
import com.iremayvaz.model.jwt.AuthResponse;
import com.iremayvaz.model.jwt.JwtService;
import com.iremayvaz.repository.EmployeeRepository;
import com.iremayvaz.repository.RefreshTokenRepository;
import com.iremayvaz.repository.RoleRepository;
import com.iremayvaz.repository.UserRepository;
import com.iremayvaz.services.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;

    @Transactional // Bir metot veya sınıfın tamamını bir işlem sayar. Hata durumunda rollback yapar. (son committen sonraki tüm değişiklikler)
    @Override
    public DtoUser register(DtoUserInsert dtoUserInsert){
        DtoUser dto = new DtoUser();
        User user = new User();

        var emailExists = userRepository.findByEmail(dtoUserInsert.getEmail());
        if(emailExists.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email zaten kayıtlı");
        } else {

            user.setEmail(dtoUserInsert.getEmail());
            user.setPassword(passwordEncoder.encode(dtoUserInsert.getPassword()));

            Role userRole = roleRepository.findByName(dtoUserInsert.getPosition())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found : " + dtoUserInsert.getPosition()));
            user.setRole(userRole);

            Employee newEmployee = saveEmployeeInfos(dtoUserInsert);
            user.attachEmployee(newEmployee);

            User savedUser = userRepository.save(user);
            BeanUtils.copyProperties(savedUser, dto);

            return dto;
        }
    }

    private Employee saveEmployeeInfos(DtoUserInsert dtoUserInsert){
        Employee employee = new Employee();

        var tcknoExists = employeeRepository.findByTckno(dtoUserInsert.getTck_no());
        var phoneExists = employeeRepository.findByPhone(dtoUserInsert.getPhoneNum());

        if(tcknoExists.isPresent() || phoneExists.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email zaten kayıtlı");
        } else {
            employee.setTck_no(dtoUserInsert.getTck_no());
            employee.setFirstName(dtoUserInsert.getFirstName());
            employee.setLastName(dtoUserInsert.getLastName());
            employee.setPhoneNum(dtoUserInsert.getPhoneNum());
            employee.setGender(dtoUserInsert.getGender());

            return employee;
        }
    }

    private RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000*60*60)); // 1 saat
        refreshToken.setUser(user);

        return refreshToken;
    }

    @Transactional
    @Override
    public AuthResponse login(AuthRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()); // Email, şifre

        Authentication authentication = authenticationProvider.authenticate(authenticationToken); // UserDetailsService'ten DB'ye yükler ve şifreyi doğrular.

        var principal = (UserDetails) authentication.getPrincipal(); // doğrulanmış kullanıcı
        String accessToken = jwtService.generateToken(principal);

        var user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı!"));

        RefreshToken refreshToken = createRefreshToken(user);
        refreshTokenRepository.save(refreshToken);

        return new AuthResponse(accessToken, refreshToken.getRefreshToken());
    }
}
