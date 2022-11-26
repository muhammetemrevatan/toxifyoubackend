package com.memrevatan.toxifyou.business.abstracts;

import com.memrevatan.toxifyou.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthService {

    ResponseEntity<?> handleAuthentication(User user);
}
