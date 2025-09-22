package com.iremayvaz.services;

import com.iremayvaz.model.dto.DtoUser;
import com.iremayvaz.model.dto.DtoUserInsert;
import com.iremayvaz.model.jwt.AuthRequest;
import com.iremayvaz.model.jwt.AuthResponse;

public interface AuthService {

    public DtoUser register(DtoUserInsert dtoUserInsert); // Giriş ekranına yönlendir

    public AuthResponse login(AuthRequest existingEmployee); // LOGIN
}
