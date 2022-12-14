package com.memrevatan.toxifyou.business.abstracts;

import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.UserDisplayNameDto;
import com.memrevatan.toxifyou.entities.userViewModel.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> createUser(User user);

    Page<User> getUsers(Pageable page, User user);

    User getByUsername(String username);

    User updatedUser(UserDisplayNameDto userDisplayNameDto, String username);
}
