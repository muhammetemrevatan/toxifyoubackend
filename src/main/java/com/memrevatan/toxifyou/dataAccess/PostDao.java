package com.memrevatan.toxifyou.dataAccess;

import com.memrevatan.toxifyou.entities.Post;
import com.memrevatan.toxifyou.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostDao extends JpaRepository<Post,Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findByUser(User user, Pageable page);

    Page<Post> findByIdLessThan(long id, Pageable page); // gönderilen id için daha öncelerili alır. örnek:3 verilirse 1 ve 2 nolu idleri döner.

    Page<Post> findByIdLessThanAndUser(long id, User user, Pageable page);

    long countByIdGreaterThan(long id);

    long countByIdGreaterThanAndUser(long id, User user);

    List<Post> findByIdGreaterThan(long id, Sort sort);

    List<Post> findByIdGreaterThanAndUser(long id, User user, Sort sort);
}
