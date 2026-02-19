package com.jose.inmobiliaria.property.user_service.api.controller;

import com.jose.inmobiliaria.property.user_service.application.service.CustomerService;
import com.jose.inmobiliaria.property.user_service.domain.entity.Customer;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/me")
    public Customer getMyProfile(Authentication authentication) {

        String email = authentication.getName();

        return customerService.getCustomerByEmail(email);
    }
}
