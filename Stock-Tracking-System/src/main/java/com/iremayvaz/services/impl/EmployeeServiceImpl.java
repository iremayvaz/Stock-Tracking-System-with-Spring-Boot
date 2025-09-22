package com.iremayvaz.services.impl;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoUserUpdate;
import com.iremayvaz.model.dto.DtoUserInsert;
import com.iremayvaz.model.entity.Employee;
import com.iremayvaz.model.entity.Role;
import com.iremayvaz.model.entity.User;
import com.iremayvaz.repository.EmployeeRepository;
import com.iremayvaz.repository.RefreshTokenRepository;
import com.iremayvaz.repository.RoleRepository;
import com.iremayvaz.repository.UserRepository;
import com.iremayvaz.repository.specifications.EmployeeSpecifications;
import com.iremayvaz.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; // BCrypt bean'i gelecek

    @Override
    @Transactional(readOnly=true)
    public List<DtoEmployee> filterEmployee(String column, String content) {
        List<DtoEmployee> dtoEmployees = new ArrayList<>();

        var spec = EmployeeSpecifications.filterByColumn(column, content);
        List<Employee> filteredEmployees = employeeRepository.findAll(spec);

        for(Employee e: filteredEmployees){
            DtoEmployee dto = new DtoEmployee();
            dto.setId(e.getId());
            dto.setFirstName(e.getFirstName());
            dto.setLastName(e.getLastName());

            if (e.getUser() != null) {
                dto.setEmail(e.getUser().getEmail());
                if (e.getUser().getRole() != null && e.getUser().getRole().getName() != null) {
                    dto.setRoleName(e.getUser().getRole().getName().name());
                }
            }
            dtoEmployees.add(dto);
        }

        return dtoEmployees;
    }

    @Transactional
    @Override
    public DtoEmployee updateEmployeeInfos(Long id, DtoUserUpdate updateUserRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı"));

        if (!user.getEmail().equals(updateUserRequest.getEmail())) {
            if (userRepository.existsByEmailAndIdNot(user.getEmail(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email zaten kayıtlı");
            }
            user.setEmail(updateUserRequest.getEmail());
        }

        // Şifre boş/null gelirse değiştirme
        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        if (updateUserRequest.getPosition() != null && user.getRole().getName() != updateUserRequest.getPosition()) {
            Role role = roleRepository.findByName(updateUserRequest.getPosition())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol bulunamadı"));
            user.setRole(role);
        }

        Employee e = user.getEmployee();
        if (e == null) {
            e = new Employee();
            e.setId(user.getId()); // @MapsId
            e.setUser(user);
            user.setEmployee(e);
        }

        e.setTck_no(updateUserRequest.getTck_no());
        e.setFirstName(updateUserRequest.getFirstName());
        e.setLastName(updateUserRequest.getLastName());
        e.setPhoneNum(updateUserRequest.getPhoneNum());
        e.setGender(updateUserRequest.getGender());

        userRepository.save(user);

        return new DtoEmployee(
                user.getId(),
                e.getFirstName(),
                e.getLastName(),
                user.getRole().getName().name(),
                user.getEmail()
        );
    }

    @Transactional(readOnly=true)
    @Override
    public DtoUserUpdate getEmployeeInfo(Long id) { // update'i otomatik doldurmak için yazdım. değiştirilecek kısım silinip değiştirilebilir.
        Optional<Employee> optional = employeeRepository.findById(id);

        if (optional.isPresent()){
            Employee employee = optional.get();
            DtoUserUpdate dto = new DtoUserUpdate();

            // Employee verileri
            dto.setTck_no(employee.getTck_no());        // tckno
            dto.setFirstName(employee.getFirstName());  // ad
            dto.setLastName(employee.getLastName());    // soyad
            dto.setPhoneNum(employee.getPhoneNum());    // telno
            dto.setGender(employee.getGender());        // cinsiyet

            // User verileri (Employee'nin User'ını al)
            if (employee.getUser() != null) {
                dto.setEmail(employee.getUser().getEmail()); // mail

                if (employee.getUser().getRole() != null) {
                    dto.setPosition(employee.getUser().getRole().getName()); // pozisyon
                }
            }

            return dto;
        } else {
            throw new IllegalArgumentException("Id ile kayıtlı kullanıcı yok!");
        }
    }

    @Transactional
    @Override
    public void deleteEmployee(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            refreshTokenRepository.deleteByUserId(optional.get().getId()); // önce kullanıcıya ait token'ları siliyoruz
            userRepository.delete(optional.get()); // sonra kullanıcıyı
        } else {
            throw new IllegalArgumentException("Kullanıcı silinemedi. Böyle bir çalışan kaydı yok!");
        }
    }
}
