package com.iremayvaz.controller;

import com.iremayvaz.model.dto.DtoUser;
import com.iremayvaz.model.dto.DtoUserIU;
import com.iremayvaz.model.jwt.AuthRequest;
import com.iremayvaz.model.jwt.AuthResponse;
import com.iremayvaz.model.jwt.RefreshTokenRequest;

public interface RestAuthController {

    public DtoUser register(DtoUserIU dtoUserIU); // Giriş ekranına yönlendir

    public AuthResponse login(AuthRequest existingUser); // LOGIN

    public AuthResponse refreshToken(RefreshTokenRequest request);
}
