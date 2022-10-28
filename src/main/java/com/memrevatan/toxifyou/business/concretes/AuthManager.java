package com.memrevatan.toxifyou.business.concretes;

import com.fasterxml.jackson.annotation.JsonView;
import com.memrevatan.toxifyou.business.abstracts.AuthService;
import com.memrevatan.toxifyou.core.httpResponse.error.ApiError;
import com.memrevatan.toxifyou.core.jsonView.BaseView;
import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthManager implements AuthService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    public AuthManager(UserDao userDao) {
        this.userDao = userDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity<?> handleAuthentication(String authorization) {
        if(authorization == null) {
            ApiError apiError = new ApiError(401,"Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        }
        String base64encoded = authorization.split("Basic ")[1];
        String decoded = new String(Base64.getDecoder().decode(base64encoded));
        String[] parts = decoded.split(":");
        String username = parts[0];
        String password = parts[1];
        User inDB = userDao.findByUsername(username);
        if(inDB == null) {
            ApiError apiError = new ApiError(401,"Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        }
        String hashedPassword = inDB.getPassword();
        if(!passwordEncoder.matches(password,hashedPassword)) {
            ApiError apiError = new ApiError(401,"Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        }
        return ResponseEntity.ok(inDB);
    }
}
