package com.memrevatan.toxifyou.business.abstracts;

import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.AuthResponse;
import com.memrevatan.toxifyou.entities.userViewModel.Credentials;
import com.memrevatan.toxifyou.entities.userViewModel.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    AuthResponse handleAuthentication(Credentials credentials);

    UserDetails getUserDetails(String token);
}
