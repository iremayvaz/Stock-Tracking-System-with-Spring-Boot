package com.iremayvaz.repository.specifications;

import com.iremayvaz.model.entity.Employee;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;


@UtilityClass
// Sınıfı final yapar
// Tüm alan ve metotlar otomatik static olur
// Default constructor private olur
// Yani bu sınıf sadece yardımcı metotlar içerir demektir
public class EmployeeSpecifications {
    public Specification<Employee> filterByColumn(String column, String content) {
        return (root, // sorgunun kökü : FROM Employee p
                query, // Tüm sorgu iskeleti (SELECT - FROM - WHERE - ORDER BY)
                criteriaBuilder) -> { // WHERE koşulunu kurarkenki araç kutusu
            if (column == null || content == null || content.trim().isEmpty()) {
                return criteriaBuilder.conjunction(); // TRUE döner yani her şeyi geçir demek
            }

            String pattern = "%" + content.toLowerCase() + "%";

            try {
                switch (column) {
                    case "firstName" -> {
                        return criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), pattern);
                    }
                    case "lastName" -> {
                        return criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), pattern);
                    }
                    case "email" -> {
                        var u = root.join("user"); // @OneToOne user
                        return criteriaBuilder.like(criteriaBuilder.lower(u.get("email")), pattern);
                    }
                    case "role" -> {
                        var u = root.join("user");
                        var r = u.join("role");
                        return criteriaBuilder.like(criteriaBuilder.lower(r.get("name").as(String.class)), pattern);
                    }
                    default -> {
                        // Bilinmeyen kolon → boş sonuç
                        return criteriaBuilder.disjunction();
                    }
                }
            } catch (IllegalArgumentException ex) {
                return criteriaBuilder.disjunction();
            }
        };
    }
}
