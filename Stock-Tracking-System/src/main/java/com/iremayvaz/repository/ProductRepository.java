package com.iremayvaz.repository;

import com.iremayvaz.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
                                            JpaSpecificationExecutor<Product> {
    // JpaRepository içindeki metotlar yetmediğinde kendi özel metotlarımızı buraya yazacağız!!!

    /*
     * HQL : sınıfın ismi ve değişken isimleri kullanılarak sorgular yazılır. (nativeQuery = false) DEFAULT
     * SQL : db'deki tablo(sema_adi.tablo_adi) ve sütun isimleri kullanılır. (nativeQuery = true)
     * */

    @Query(value = "FROM Product p WHERE p.barcode = :barcode")
    Optional<Product> findByBarcode(String barcode);
}
