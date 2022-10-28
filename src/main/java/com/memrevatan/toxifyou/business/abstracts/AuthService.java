package com.memrevatan.toxifyou.business.abstracts;

import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> handleAuthentication(String authorization);
}
