package com.memrevatan.toxifyou.business.abstracts;

import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthService {

    UserDto handleAuthentication(User user);
}
