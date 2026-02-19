package com.jose.inmobiliaria.property.user_service.controller;

import com.jose.inmobiliaria.property.common.security.JwtService;
import com.jose.inmobiliaria.property.user_service.api.dto.LoginRequest;
import com.jose.inmobiliaria.property.user_service.application.service.CustomUserDetailsService;
import com.jose.inmobiliaria.property.user_service.application.service.RefreshTokenService;
import com.jose.inmobiliaria.property.user_service.domain.entity.RefreshToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          CustomUserDetailsService customUserDetailsService,
                          RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // Revocamos cualquier token anterior
        refreshTokenService.revokeAllTokensForUser(userDetails.getUsername());

        // Guardamos el nuevo refresh token
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setUsername(userDetails.getUsername());
        tokenEntity.setRevoked(false);
        tokenEntity.setExpirationDate(
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7) // 7 días
        );

        refreshTokenService.saveRefreshToken(tokenEntity);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    // REFRESH
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "refreshToken is required"));
        }

        RefreshToken tokenEntity = refreshTokenService.getByToken(refreshToken);

        if (tokenEntity == null || tokenEntity.isRevoked() || tokenEntity.getExpirationDate().before(new Date())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Refresh token is invalid or expired"));
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(tokenEntity.getUsername());
        String newAccessToken = jwtService.generateAccessToken(userDetails);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "refreshToken is required"));
        }

        refreshTokenService.revokeRefreshToken(refreshToken);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
