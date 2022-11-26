package com.memrevatan.toxifyou.api.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.memrevatan.toxifyou.business.abstracts.AuthService;
import com.memrevatan.toxifyou.core.annotation.CurrentUser;
import com.memrevatan.toxifyou.core.httpResponse.error.ApiError;
import com.memrevatan.toxifyou.core.jsonView.BaseView;
import com.memrevatan.toxifyou.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/1.0/auth")
    @JsonView(BaseView.Base.class)
    public ResponseEntity<?> handleAuthentication(@CurrentUser User user) {
        return this.authService.handleAuthentication(user);
    }

}
