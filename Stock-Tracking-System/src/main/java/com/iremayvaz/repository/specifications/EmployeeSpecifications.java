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

            try{
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(column)),
                        "%" + content.toLowerCase() + "%"
                );
            } catch (IllegalArgumentException e) {
                return criteriaBuilder.disjunction(); // FALSE döner yani boş kayıt döner
            }
        };
    }
}
