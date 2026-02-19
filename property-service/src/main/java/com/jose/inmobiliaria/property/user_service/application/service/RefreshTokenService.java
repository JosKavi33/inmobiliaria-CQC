package com.jose.inmobiliaria.property.user_service.application.service;

import com.jose.inmobiliaria.property.user_service.domain.entity.RefreshToken;
import com.jose.inmobiliaria.property.user_service.infrastructure.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken saveRefreshToken(RefreshToken token) {
        return refreshTokenRepository.save(token);
    }

    public RefreshToken getByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElse(null);
    }

    public void revokeRefreshToken(String token) {
        RefreshToken rt = getByToken(token);
        if (rt != null) {
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
        }
    }

    public void revokeAllTokensForUser(String username) {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUsernameAndRevokedFalse(username);
        for (RefreshToken t : tokens) {
            t.setRevoked(true);
        }
        refreshTokenRepository.saveAll(tokens);
    }
}
