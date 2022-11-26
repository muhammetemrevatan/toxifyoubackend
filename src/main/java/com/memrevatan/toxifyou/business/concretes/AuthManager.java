package com.memrevatan.toxifyou.business.concretes;

import com.memrevatan.toxifyou.business.abstracts.AuthService;
import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthManager implements AuthService {

    private UserDao userDao;
    private User user;

    @Autowired
    public AuthManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ResponseEntity<?> handleAuthentication(User user) {
        return ResponseEntity.ok(user);
    }
}
