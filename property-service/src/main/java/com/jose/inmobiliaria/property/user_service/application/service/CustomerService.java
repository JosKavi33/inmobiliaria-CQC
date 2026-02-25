package com.jose.inmobiliaria.property.user_service.application.service;

import com.jose.inmobiliaria.property.user_service.domain.entity.Customer;
import com.jose.inmobiliaria.property.user_service.infrastructure.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerByEmail(String email) {
        log.warn("Customer not found: {}", email);
        return customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
