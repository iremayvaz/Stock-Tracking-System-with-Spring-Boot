package com.iremayvaz.services.impl;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoEmployeeIU;
import com.iremayvaz.model.entity.Employee;
import com.iremayvaz.repository.EmployeeRepository;
import com.iremayvaz.repository.specifications.EmployeeSpecifications;
import com.iremayvaz.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly=true)
    public List<DtoEmployee> filterEmployee(String column, String content) {
        DtoEmployee dto = new DtoEmployee();
        List<DtoEmployee> dtoEmployees = new ArrayList<>();

        var spec = EmployeeSpecifications.filterByColumn(column, content);
        List<Employee> filteredEmployees = employeeRepository.findAll(spec);

        for(Employee e: filteredEmployees){
            BeanUtils.copyProperties(e, dto);
            dtoEmployees.add(dto);
        }

        return dtoEmployees;
    }

    @Transactional
    @Override
    public DtoEmployee updateEmployeeInfos(String email, DtoEmployeeIU updateEmployeeRequest) {
        DtoEmployee dto = new DtoEmployee();

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(""));

        if(employee.getLastName().equals(updateEmployeeRequest.getLastName())){ employee.setLastName(updateEmployeeRequest.getLastName());}
        if(employee.getPhoneNum().equals(updateEmployeeRequest.getPhoneNum())){ employee.setPhoneNum(updateEmployeeRequest.getPhoneNum());}
        if(employee.getFirstName().equals(updateEmployeeRequest.getFirstName())){ employee.setFirstName(updateEmployeeRequest.getFirstName());}
        if(employee.getTck_no().equals(updateEmployeeRequest.getTck_no())){ employee.setTck_no(updateEmployeeRequest.getTck_no());}
        if(employee.getGender().equals(updateEmployeeRequest.getGender())){ employee.setGender(updateEmployeeRequest.getGender());}

        Employee savedEmployee = employeeRepository.save(employee);

        BeanUtils.copyProperties(savedEmployee, dto);
        return dto;
    }

    @Transactional(readOnly=true)
    @Override
    public DtoEmployee getEmployeeInfo(String email) { // update'i otomatik doldurmak için yazdım. değiştirilecek kısım silinip değiştirilebilir.
        DtoEmployee dto = new DtoEmployee();
        Optional<Employee> optional = employeeRepository.findByEmail(email);

        if (optional.isPresent()){
            BeanUtils.copyProperties(optional.get(), dto);
            return dto;
        } else {
            throw new IllegalArgumentException("Email ile kayıtlı kullanıcı yok!");
        }
    }

    @Transactional
    @Override
    public void deleteEmployee(String email) {
        Optional<Employee> optional = employeeRepository.findByEmail(email);
        if(optional.isPresent()){
            employeeRepository.delete(optional.get());
        } else {
            throw new IllegalArgumentException("Yanlış email girdiniz veya böyle bir çalışan kaydı yok!");
        }
    }
}
