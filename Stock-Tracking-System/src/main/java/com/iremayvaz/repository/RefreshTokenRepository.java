package com.iremayvaz.repository;

import com.iremayvaz.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query(value = "from RefreshToken r WHERE r.refreshToken = :refreshToken")
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
