package com.memrevatan.toxifyou.dataAccess;

import com.memrevatan.toxifyou.entities.OtpCodeTransaction;
import com.memrevatan.toxifyou.entities.Post;
import com.memrevatan.toxifyou.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OtpCodeTransactionDao extends JpaRepository<OtpCodeTransaction,Long> {

    Optional<OtpCodeTransaction> findByUserId(long id);
}
