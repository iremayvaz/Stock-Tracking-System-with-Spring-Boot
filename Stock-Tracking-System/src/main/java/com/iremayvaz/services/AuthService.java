package com.iremayvaz.services;

import com.iremayvaz.model.dto.DtoUser;
import com.iremayvaz.model.dto.DtoUserIU;
import com.iremayvaz.model.jwt.AuthRequest;
import com.iremayvaz.model.jwt.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public DtoUser register(DtoUserIU dtoUserIU); // Giriş ekranına yönlendir

    public AuthResponse login(AuthRequest existingEmployee); // LOGIN
}
