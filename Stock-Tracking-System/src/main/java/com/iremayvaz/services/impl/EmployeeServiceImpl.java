package com.iremayvaz.services.impl;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoUser;
import com.iremayvaz.model.dto.DtoUserIU;
import com.iremayvaz.model.entity.Employee;
import com.iremayvaz.model.entity.User;
import com.iremayvaz.repository.EmployeeRepository;
import com.iremayvaz.repository.RoleRepository;
import com.iremayvaz.repository.UserRepository;
import com.iremayvaz.repository.specifications.EmployeeSpecifications;
import com.iremayvaz.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            BeanUtils.copyProperties(e, dto);
            dtoEmployees.add(dto);
        }

        return dtoEmployees;
    }

    @Transactional
    @Override
    public DtoEmployee updateEmployeeInfos(Long id, DtoUserIU updateUserRequest) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));

        User user = employee.getUser();
        
        // EMPLOYEE TARAFINDA
        // İSİM GÜNCELLEMESİ 
        if (updateUserRequest.getFirstName() != null && !updateUserRequest.getFirstName().isBlank()
                && !java.util.Objects.equals(employee.getFirstName(), updateUserRequest.getFirstName())) {
            employee.setFirstName(updateUserRequest.getFirstName());
        }

        // SOYİSİM GÜNCELLEMESİ 
        if (updateUserRequest.getLastName() != null && !updateUserRequest.getLastName().isBlank()
                && !java.util.Objects.equals(employee.getLastName(), updateUserRequest.getLastName())) {
            employee.setLastName(updateUserRequest.getLastName());
        }

        // TEL NO GÜNCELLEMESİ 
        if (updateUserRequest.getPhoneNum() != null && !updateUserRequest.getPhoneNum().isBlank()
                && !java.util.Objects.equals(employee.getPhoneNum(), updateUserRequest.getPhoneNum())) {
            employee.setPhoneNum(updateUserRequest.getPhoneNum());
        }

        // TCK NO GÜNCELLEMESİ 
        if (updateUserRequest.getTck_no() != null && !updateUserRequest.getTck_no().isBlank()
                && !java.util.Objects.equals(employee.getTck_no(), updateUserRequest.getTck_no())) {
            employee.setTck_no(updateUserRequest.getTck_no());
        }

        // CİNSİYET GÜNCELLEMESİ
        if (updateUserRequest.getGender() != null
                && !java.util.Objects.equals(employee.getGender(), updateUserRequest.getGender())) {
            employee.setGender(updateUserRequest.getGender());
        }

        // USER TARAFINDA
        // EMAİL GÜNCELLEMESİ 
        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isBlank()){
            if (!java.util.Objects.equals(user.getEmail(), updateUserRequest.getEmail())) {
                user.setEmail(updateUserRequest.getEmail());
            }
        }

        // ŞİFRE GÜNCELLEMESİ 
        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isBlank()) {
            if (!passwordEncoder.matches(updateUserRequest.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            }
        }

        // ROL GÜNCELLEMESİ
        if (updateUserRequest.getPosition()!=null
                && (user.getRole()==null || user.getRole().getName()!=updateUserRequest.getPosition())) {
            var role = roleRepository.findByName(updateUserRequest.getPosition())
                    .orElseThrow(() -> new IllegalArgumentException("Rol bulunamadı: " + updateUserRequest.getPosition()));
            user.setRole(role);
        }

        var saved = employeeRepository.save(employee);
        var dto = new DtoEmployee();

        dto.setId(saved.getId());
        dto.setFirstName(saved.getFirstName());
        dto.setLastName(saved.getLastName());

        // Bu iki alan Employee'de yoksa (User/Role'de ise) KENDİN DOLDURMAN gerekir:
        if (saved.getUser() != null) {
            dto.setEmail(saved.getUser().getEmail());
            if (saved.getUser().getRole() != null) {
                dto.setRoleName(saved.getUser().getRole().getName().name());
            }
        }

        return dto;
    }

    @Transactional(readOnly=true)
    @Override
    public DtoEmployee getEmployeeInfo(Long id) { // update'i otomatik doldurmak için yazdım. değiştirilecek kısım silinip değiştirilebilir.
        DtoEmployee dto = new DtoEmployee();
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
