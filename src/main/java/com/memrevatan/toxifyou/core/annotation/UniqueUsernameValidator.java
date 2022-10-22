package com.memrevatan.toxifyou.core.annotation;

import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    UserDao userDao;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        User user = userDao.findByUsername(username);
        return user == null;
    }
}
