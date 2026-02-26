package com.jose.inmobiliaria.property.user_service.application.service;

import com.jose.inmobiliaria.property.user_service.domain.entity.User;
import com.jose.inmobiliaria.property.user_service.domain.enums.RoleType;
import com.jose.inmobiliaria.property.user_service.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldLoadUserSuccessfully() {

        User user = new User();
        user.setEmail("admin@mail.com");
        user.setPassword("encryptedPassword");
        user.setRoleType(RoleType.ADMIN);

        when(userRepository.findByEmail("admin@mail.com"))
                .thenReturn(Optional.of(user));

        UserDetails result =
                customUserDetailsService.loadUserByUsername("admin@mail.com");

        assertEquals("admin@mail.com", result.getUsername());
        assertEquals("encryptedPassword", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findByEmail("notfound@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("notfound@mail.com"));
    }
}