package com.memrevatan.toxifyou.business.concretes;

import com.memrevatan.toxifyou.business.abstracts.AuthService;
import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDto handleAuthentication(User user) {
        return new UserDto(user);
    }
}
