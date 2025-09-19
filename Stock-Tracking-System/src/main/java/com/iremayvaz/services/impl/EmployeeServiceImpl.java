package com.iremayvaz.services.impl;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoEmployeeDetail;
import com.iremayvaz.model.dto.DtoUserIU;
import com.iremayvaz.model.entity.Employee;
import com.iremayvaz.model.entity.Role;
import com.iremayvaz.model.entity.User;
import com.iremayvaz.repository.EmployeeRepository;
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
    public DtoEmployee updateEmployeeInfos(Long id, DtoUserIU updateUserRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı"));

        if(updateUserRequest.getEmail() == null || updateUserRequest.getEmail().isBlank()) { throw new IllegalArgumentException("Email boş"); }

        if (!user.getEmail().equals(updateUserRequest.getEmail())) {
            if (userRepository.existsByEmail(user.getEmail())) {
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
    public DtoEmployeeDetail getEmployeeInfo(Long id) { // update'i otomatik doldurmak için yazdım. değiştirilecek kısım silinip değiştirilebilir.
        DtoEmployeeDetail dto = new DtoEmployeeDetail();
        Optional<Employee> optional = employeeRepository.findById(id);

        if (optional.isPresent()){
            BeanUtils.copyProperties(optional.get(), dto);
            return dto;
        } else {
            throw new IllegalArgumentException("Id ile kayıtlı kullanıcı yok!");
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            userRepository.delete(optional.get());
        } else {
            throw new IllegalArgumentException("Kullanıcı silinemedi. Böyle bir çalışan kaydı yok!");
        }
    }
}
