package com.iremayvaz.controller;

import com.iremayvaz.model.dto.DtoUser;
import com.iremayvaz.model.dto.DtoUserIU;
import com.iremayvaz.model.jwt.AuthRequest;
import com.iremayvaz.model.jwt.AuthResponse;
import com.iremayvaz.model.jwt.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;

public interface RestAuthController {

    public ResponseEntity<DtoUser> register(DtoUserIU dtoUserIU); // Giriş ekranına yönlendir

    public ResponseEntity<AuthResponse> login(AuthRequest existingUser); // LOGIN

    public ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest request);
}
