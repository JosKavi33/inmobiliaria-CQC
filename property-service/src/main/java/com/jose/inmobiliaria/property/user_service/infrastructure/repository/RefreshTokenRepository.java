package com.jose.inmobiliaria.property.user_service.infrastructure.repository;

import com.jose.inmobiliaria.property.user_service.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUsernameAndRevokedFalse(String username);

    ;
}

