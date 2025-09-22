package com.iremayvaz.controller;

import com.iremayvaz.model.dto.DtoUser;
import com.iremayvaz.model.dto.DtoUserInsert;
import com.iremayvaz.model.jwt.AuthRequest;
import com.iremayvaz.model.jwt.AuthResponse;
import com.iremayvaz.model.jwt.RefreshTokenRequest;
import com.iremayvaz.services.impl.AuthServiceImpl;
import com.iremayvaz.services.impl.RefreshTokenServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RestAuthControllerImpl {

    private final AuthServiceImpl authService;
    private final RefreshTokenServiceImpl refreshTokenService;


    @PostMapping("/register")
    public ResponseEntity<DtoUser> register(@RequestBody @Valid DtoUserInsert dtoUserInsert) {
        var newUser = authService.register(dtoUserInsert);
        return ResponseEntity.created(URI.create("/users/" + newUser.getEmail())).body(newUser);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest existingEmployee) {
        var newLogin = authService.login(existingEmployee);
        return ResponseEntity.accepted().body(newLogin);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        var refresh = refreshTokenService.refreshToken(request);
        return ResponseEntity.accepted().body(refresh);
    }
}
