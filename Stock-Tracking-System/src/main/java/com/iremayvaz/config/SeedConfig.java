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
            // DB'de kayıt yoksa kaydet!
            if (roleRepository.count() == 0) {

                Role boss = new Role(); // PATRON
                boss.setName(RoleName.BOSS);
                boss.setPermissions(Set.of(
                        Permission.EMPLOYEE_DELETE,
                        Permission.PRODUCT_DELETE,
                        Permission.EMPLOYEE_UPDATE,
                        Permission.PRODUCT_UPDATE,
                        Permission.EMPLOYEE_ADD,
                        Permission.PRODUCT_ADD,
                        Permission.EMPLOYEE_LIST,
                        Permission.PRODUCT_LIST,
                        Permission.ME
                ));

                Role accountant = new Role(); // MUHASEBECİ
                accountant.setName(RoleName.ACCOUNTANT);
                accountant.setPermissions(Set.of(
                        Permission.PRODUCT_DELETE,
                        Permission.PRODUCT_UPDATE,
                        Permission.PRODUCT_ADD,
                        Permission.PRODUCT_LIST,
                        Permission.ME
                ));

                Role authorized = new Role(); // YETKİLİ KİŞİ
                authorized.setName(RoleName.AUTHORIZED);
                authorized.setPermissions(Set.of(
                        Permission.EMPLOYEE_DELETE,
                        Permission.PRODUCT_DELETE,
                        Permission.EMPLOYEE_UPDATE,
                        Permission.PRODUCT_UPDATE,
                        Permission.EMPLOYEE_ADD,
                        Permission.PRODUCT_ADD,
                        Permission.EMPLOYEE_LIST,
                        Permission.PRODUCT_LIST,
                        Permission.ME
                ));

                Role consultant = new Role(); // DANIŞMAN
                consultant.setName(RoleName.CONSULTANT);
                consultant.setPermissions(Set.of(
                        Permission.EMPLOYEE_UPDATE,
                        Permission.EMPLOYEE_ADD,
                        Permission.EMPLOYEE_DELETE,
                        Permission.EMPLOYEE_LIST,
                        Permission.ME
                ));

                Role employee = new Role(); // ÇALIŞAN
                employee.setName(RoleName.EMPLOYEE);
                employee.setPermissions(Set.of(
                        Permission.PRODUCT_UPDATE,
                        Permission.EMPLOYEE_LIST,
                        Permission.PRODUCT_LIST,
                        Permission.ME

                        // kendi bilgilerini de güncellesin??
                ));

                Role secretary = new Role(); // SEKRETER
                secretary.setName(RoleName.SECRETARY);
                secretary.setPermissions(Set.of(
                        Permission.EMPLOYEE_DELETE,
                        Permission.PRODUCT_DELETE,
                        Permission.EMPLOYEE_UPDATE,
                        Permission.PRODUCT_UPDATE,
                        Permission.EMPLOYEE_ADD,
                        Permission.PRODUCT_ADD,
                        Permission.EMPLOYEE_LIST,
                        Permission.PRODUCT_LIST,
                        Permission.ME
                ));

                Role visitor = new Role(); // ZİYARETÇİ
                visitor.setName(RoleName.VISITOR);
                visitor.setPermissions(Set.of(
                        Permission.EMPLOYEE_LIST,
                        Permission.PRODUCT_LIST,
                        Permission.ME
                ));

                // Tüm rolleri - izinleri veri tabanına kaydet
                roleRepository.saveAll(List.of(boss, accountant, authorized, consultant, employee, secretary, visitor));
            } else { // DB'de kayıt varsa
                return;
            }
        };
    }
}
