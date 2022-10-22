package com.memrevatan.toxifyou.business.concretes;

import com.memrevatan.toxifyou.business.abstracts.UserService;
import com.memrevatan.toxifyou.core.httpResponse.success.ApiSuccess;
import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserManager implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity<?> createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        log.info("User created! " + user + " " + LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(200,"User created!","/api/1.0/users"));
    }
}
