package com.memrevatan.toxifyou.business.abstracts;

import com.memrevatan.toxifyou.entities.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> createUser(User user);
}
