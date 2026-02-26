package com.jose.inmobiliaria.property.user_service;

import com.jose.inmobiliaria.property.user_service.domain.entity.Customer;
import com.jose.inmobiliaria.property.user_service.domain.entity.User;
import com.jose.inmobiliaria.property.user_service.domain.enums.RoleType;
import com.jose.inmobiliaria.property.user_service.infrastructure.repository.CustomerRepository;
import com.jose.inmobiliaria.property.user_service.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class CustomerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldSaveUserAndCustomerCorrectly() {

        User user = new User();
        user.setEmail("integration@test.com");
        user.setPassword("123456");
        user.setRoleType(RoleType.USER);

        Customer customer = new Customer();
        customer.setFirstName("Integration");
        customer.setLastName("Test");
        customer.setUser(user);

        user.setCustomer(customer);

        userRepository.save(user);

        User savedUser = userRepository.findByEmail("integration@test.com")
                .orElseThrow();

        assertNotNull(savedUser);
        assertEquals("integration@test.com", savedUser.getEmail());

        assertNotNull(savedUser.getCustomer());
        assertEquals("Integration", savedUser.getCustomer().getFirstName());
    }
}