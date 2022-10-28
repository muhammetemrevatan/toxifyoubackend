package com.memrevatan.toxifyou.api.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.memrevatan.toxifyou.business.abstracts.AuthService;
import com.memrevatan.toxifyou.core.jsonView.BaseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @CrossOrigin
    @PostMapping("/api/1.0/auth")
    @JsonView(BaseView.Base.class)
    public ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization", required = false) String authorization) {
        return this.authService.handleAuthentication(authorization);
    }
}
