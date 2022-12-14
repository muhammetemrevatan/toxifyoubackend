package com.memrevatan.toxifyou.business.concretes;

import com.memrevatan.toxifyou.business.abstracts.UserService;
import com.memrevatan.toxifyou.core.file.FileManager;
import com.memrevatan.toxifyou.core.httpResponse.error.ApiError;
import com.memrevatan.toxifyou.core.httpResponse.error.NotFoundException;
import com.memrevatan.toxifyou.core.httpResponse.success.ApiSuccess;
import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.UserDisplayNameDto;
import com.memrevatan.toxifyou.entities.userViewModel.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class UserManager implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    private FileManager fileManager;

    @Autowired
    public UserManager(UserDao userDao, PasswordEncoder passwordEncoder, FileManager fileManager) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.fileManager = fileManager;
    }

    @Override
    public ResponseEntity<?> createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        log.info("User created! " + user + " " + LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(200, "User created!", "/api/1.0/users"));
    }

    @Override
    public Page<User> getUsers(Pageable page, User user) {
        if (user != null) {
            return userDao.findByUsernameNot(user.getUsername(), page);
        }
        return userDao.findAll(page);
    }

    @Override
    public User getByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    @Override
    public User updatedUser(UserDisplayNameDto updatedUser, String username) {
        User user = userDao.findByUsername(username);
        user.setDisplayName(updatedUser.getDisplayName());
        if(updatedUser.getImage() != null) {
            String oldImageName = user.getImage();
            try {
                String storedFileName = fileManager.writeBase64EncodedStringToFile(updatedUser.getImage());
                user.setImage(storedFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileManager.deleteFile(oldImageName);
        }
        return userDao.save(user);
    }
}
