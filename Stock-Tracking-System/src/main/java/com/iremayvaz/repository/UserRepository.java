package com.iremayvaz.repository;

import com.iremayvaz.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"role", "role.permissions"}) // Kendi join yapar
    @Query("FROM User WHERE email = :email")
    Optional<User> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}

