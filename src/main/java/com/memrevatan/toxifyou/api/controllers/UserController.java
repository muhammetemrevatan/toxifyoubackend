package com.memrevatan.toxifyou.api.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.memrevatan.toxifyou.business.abstracts.UserService;
import com.memrevatan.toxifyou.core.abstracts.UserProjection;
import com.memrevatan.toxifyou.core.httpResponse.error.ApiError;
//import com.memrevatan.toxifyou.core.jsonView.BaseView;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/1.0/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        return this.userService.createUser(user);
    }

    @GetMapping("/api/1.0/users")
//    @JsonView(BaseView.Base.class)
    public Page<UserDto> getUsers(Pageable page) {
        return this.userService.getUsers(page).map(UserDto::new);
    }

}
