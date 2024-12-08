package com.clients.api.rest.management.repository;

import com.clients.api.rest.management.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findByToken(String username);

    UserToken findByUsername(String username);

    void deleteByToken(String token);
}
