package com.iremayvaz.controller.impl;

import com.iremayvaz.controller.RestAuthController;
import com.iremayvaz.model.dto.DtoUser;
import com.iremayvaz.model.dto.DtoUserIU;
import com.iremayvaz.model.jwt.AuthRequest;
import com.iremayvaz.model.jwt.AuthResponse;
import com.iremayvaz.model.jwt.RefreshTokenRequest;
import com.iremayvaz.services.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestAuthControllerImpl implements RestAuthController {

    private final AuthServiceImpl authService;

    @Override
    @PostMapping("/register")
    public DtoUser register(DtoUserIU dtoUserIU) {
        return authService.register(dtoUserIU);
    }

    @Override
    @PutMapping("/login")
    public AuthResponse login(AuthRequest existingEmployee) {
        return authService.login(existingEmployee);
    }

    @Override
    @PostMapping("/refreshToken")
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        return null;
    }
}
