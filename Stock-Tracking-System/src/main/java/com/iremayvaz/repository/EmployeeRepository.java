package com.iremayvaz.repository;

import com.iremayvaz.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>,
                                            JpaSpecificationExecutor<Employee> {
    // JpaRepository içindeki metotlar yetmediğinde kendi özel metotlarımızı buraya yazacağız!!!

    /*
     * HQL : sınıfın ismi ve değişken isimleri kullanılarak sorgular yazılır. (nativeQuery = false)
     * SQL : db'deki tablo(sema_adi.tablo_adi) ve sütun isimleri kullanılır. (nativeQuery = true)
     * */

    @Query("SELECT e FROM Employee e WHERE e.user.email = :email")
    Optional<Employee> findByEmail(@Param("email") String email);
}
