package com.memrevatan.toxifyou.api.controllers;

import com.memrevatan.toxifyou.business.abstracts.AuthService;
import com.memrevatan.toxifyou.core.annotation.CurrentUser;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/1.0/auth")
//    @JsonView(BaseView.Base.class)
    public UserDto handleAuthentication(@CurrentUser User user) {
        return this.authService.handleAuthentication(user);
    }

}
