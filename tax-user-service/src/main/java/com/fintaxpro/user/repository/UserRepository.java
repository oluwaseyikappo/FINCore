package com.fintaxpro.user.repository;

import com.fintaxpro.user.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Logger log = LoggerFactory.getLogger(UserRepository.class);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
