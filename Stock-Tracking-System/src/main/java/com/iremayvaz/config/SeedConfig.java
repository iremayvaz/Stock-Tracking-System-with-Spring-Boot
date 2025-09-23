package com.iremayvaz.config;

import com.iremayvaz.model.entity.Role;
import com.iremayvaz.model.entity.enums.Permission;
import com.iremayvaz.model.entity.enums.RoleName;
import com.iremayvaz.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SeedConfig {
    // Uygulama çalıştırıldığında veritabanına temel rol - izin blgilerini yükler.

    private final RoleRepository roleRepository;

    @Transactional
    @Bean
    public CommandLineRunner seed(){ // CommandLineRunner : Uyg başladıktan sonra 1 kez çalışır.
        return args -> {
            Map<RoleName, Set<Permission>> matrix = Map.of(

                RoleName.BOSS, Set.of(
                        Permission.EMPLOYEE_DELETE,
                        Permission.PRODUCT_DELETE,
                        Permission.EMPLOYEE_UPDATE,
                        Permission.PRODUCT_UPDATE,
                        Permission.EMPLOYEE_ADD,
                        Permission.PRODUCT_ADD,
                        Permission.EMPLOYEE_LIST,
                        Permission.PRODUCT_LIST
                        
                ),// PATRON

                RoleName.ACCOUNTANT, Set.of(
                        Permission.PRODUCT_DELETE,
                        Permission.PRODUCT_UPDATE,
                        Permission.PRODUCT_ADD,
                        Permission.PRODUCT_LIST
                        
                ),// MUHASEBECİ

                RoleName.AUTHORIZED, Set.of(
                        Permission.EMPLOYEE_DELETE,
                        Permission.PRODUCT_DELETE,
                        Permission.EMPLOYEE_UPDATE,
                        Permission.PRODUCT_UPDATE,
                        Permission.EMPLOYEE_ADD,
                        Permission.PRODUCT_ADD,
                        Permission.EMPLOYEE_LIST,
                        Permission.PRODUCT_LIST
                        
                ), // YETKİLİ KİŞİ

                RoleName.CONSULTANT, Set.of(
                        Permission.EMPLOYEE_UPDATE,
                        Permission.EMPLOYEE_ADD,
                        Permission.EMPLOYEE_DELETE,
                        Permission.EMPLOYEE_LIST
                        
                ), // DANIŞMAN

                RoleName.EMPLOYEE, Set.of(
                        Permission.PRODUCT_UPDATE,
                        Permission.PRODUCT_ADD,
                        Permission.PRODUCT_LIST

                        // kendi bilgilerini de güncellesin??
                ), // ÇALIŞAN

                RoleName.SECRETARY, Set.of(
                        Permission.EMPLOYEE_DELETE,
                        Permission.PRODUCT_DELETE,
                        Permission.EMPLOYEE_UPDATE,
                        Permission.PRODUCT_UPDATE,
                        Permission.EMPLOYEE_ADD,
                        Permission.PRODUCT_ADD,
                        Permission.EMPLOYEE_LIST,
                        Permission.PRODUCT_LIST
                        
                ), // SEKRETER

                RoleName.VISITOR, Set.of(
                        Permission.PRODUCT_LIST
                        
                ) // ZİYARETÇİ
            );

            // Tüm rolleri - izinleri veri tabanına kaydet
            matrix.forEach(this::upsertRole);
        };
    }

    @Transactional
    protected void upsertRole(RoleName roleName, Set<Permission> permissions) {
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName(roleName);
                    return r;
                });

        // İdempotent: her çalıştırmada hedef set’e eşitle
        role.getPermissions().clear();
        role.getPermissions().addAll(permissions);
        roleRepository.save(role); // yoksa INSERT, varsa UPDATE
    }
}
