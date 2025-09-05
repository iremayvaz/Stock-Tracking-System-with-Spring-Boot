package com.iremayvaz.services.impl;

import com.iremayvaz.model.dto.DtoUser;
import com.iremayvaz.model.dto.DtoUserIU;
import com.iremayvaz.model.entity.Employee;
import com.iremayvaz.model.entity.RefreshToken;
import com.iremayvaz.model.entity.User;
import com.iremayvaz.model.jwt.AuthRequest;
import com.iremayvaz.model.jwt.AuthResponse;
import com.iremayvaz.model.jwt.JwtService;
import com.iremayvaz.repository.EmployeeRepository;
import com.iremayvaz.repository.RefreshTokenRepository;
import com.iremayvaz.repository.UserRepository;
import com.iremayvaz.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Override
    public DtoUser register(DtoUserIU dtoUserIU){
        DtoUser dto = new DtoUser();
        User user = new User();

        user.setEmail(dtoUserIU.getEmail());
        user.setPassword(passwordEncoder.encode(dtoUserIU.getPassword()));

        Employee newEmployee = saveEmployeeInfos(dtoUserIU);
        user.attachEmployee(newEmployee);

        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, dto);

        return dto;
    }

    private Employee saveEmployeeInfos(DtoUserIU dtoUserIU){
        Employee employee = new Employee();

        employee.setTck_no(dtoUserIU.getTck_no());
        employee.setFirstName(dtoUserIU.getFirstName());
        employee.setLastName(dtoUserIU.getLastName());
        employee.setPhoneNum(dtoUserIU.getPhoneNum());
        employee.setEmail(dtoUserIU.getEmail());
        employee.setPosition(dtoUserIU.getPosition());
        employee.setGender(dtoUserIU.getGender());

        return employee;
    }

    @Override
    public AuthResponse login(AuthRequest existingUser) {
        return null;
    }
}
