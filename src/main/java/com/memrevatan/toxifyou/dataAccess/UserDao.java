package com.memrevatan.toxifyou.dataAccess;

import com.memrevatan.toxifyou.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Page<User> findByUsernameNot(String user, Pageable page);
}
