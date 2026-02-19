package com.jose.inmobiliaria.property.user_service.infrastructure.repository;

import com.jose.inmobiliaria.property.user_service.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserEmail(String email);

}
