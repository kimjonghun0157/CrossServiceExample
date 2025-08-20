package com.dtcenter.wsa_cofas_ema_server.rest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<Object> findByUsername(String username);
}