package com.iremayvaz.controller;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoUserUpdate;
import com.iremayvaz.services.impl.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee API", description = "Çalışan işlemleri")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class RestEmployeeControllerImpl{

    private final EmployeeServiceImpl employeeService;

    @Operation(description = "Çalışan filtreleme")
    @PreAuthorize( "hasAuthority('EMPLOYEE_LIST') and hasAnyRole('ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'AUTHORIZED')" )
    @GetMapping("/filter")
    public List<DtoEmployee> filterEmployee(@RequestParam(required = false) String column,
                                            @RequestParam(required = false) String content) {
        return employeeService.filterEmployee(column, content);
    }

    @Operation(description = "Çalışan silme")
    @PreAuthorize( "hasAuthority('EMPLOYEE_DELETE') and hasAnyRole('SECRETARY', 'BOSS', 'AUTHORIZED')" )
    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable(value = "id") @NotNull Long id) {
        employeeService.deleteEmployee(id);
    }

    @Operation(description = "Çalışan güncelleme")
    @PreAuthorize( "hasAuthority('EMPLOYEE_UPDATE') and hasAnyRole('SECRETARY', 'BOSS', 'AUTHORIZED')" )
    @PutMapping("/update/{id}")
    public DtoEmployee updateEmployeeInfos(@PathVariable(value = "id") @NotNull Long id,
                                           @RequestBody @Valid DtoUserUpdate updateUserRequest) {
        return employeeService.updateEmployeeInfos(id, updateUserRequest);
    }

    @Operation(description = "ID ile çalışan bilgisi alma")
    @PreAuthorize( "hasAuthority('EMPLOYEE_LIST') and hasAnyRole('SECRETARY', 'BOSS', 'AUTHORIZED', 'CONSULTANT')" )
    @GetMapping("/{id}")
    public DtoUserUpdate getEmployeeInfo(@PathVariable(value = "id") @NotNull Long id) {
        return employeeService.getEmployeeInfo(id);
    }
}
