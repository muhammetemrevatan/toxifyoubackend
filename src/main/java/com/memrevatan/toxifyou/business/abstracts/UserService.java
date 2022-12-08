package com.memrevatan.toxifyou.business.abstracts;

import com.memrevatan.toxifyou.core.abstracts.UserProjection;
import com.memrevatan.toxifyou.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<?> createUser(User user);

    Page<User> getUsers(Pageable page);
}
