package com.jose.inmobiliaria.property.user_service.application.service;

import com.jose.inmobiliaria.property.user_service.domain.entity.Customer;
import com.jose.inmobiliaria.property.user_service.infrastructure.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldReturnCustomerWhenEmailExists() {

        Customer customer = new Customer();

        when(customerRepository.findByUserEmail("test@mail.com"))
                .thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerByEmail("test@mail.com");

        assertSame(customer, result); // mejor que validar campos
        verify(customerRepository).findByUserEmail("test@mail.com");
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        when(customerRepository.findByUserEmail("notfound@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> customerService.getCustomerByEmail("notfound@mail.com"));

        verify(customerRepository).findByUserEmail("notfound@mail.com");
    }
}