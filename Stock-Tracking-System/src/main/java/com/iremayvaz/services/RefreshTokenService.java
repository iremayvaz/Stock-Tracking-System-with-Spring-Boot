package com.iremayvaz.services;

import com.iremayvaz.model.jwt.AuthResponse;
import com.iremayvaz.model.jwt.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;

public interface RefreshTokenService {

    public AuthResponse refreshToken(RefreshTokenRequest request);
}
