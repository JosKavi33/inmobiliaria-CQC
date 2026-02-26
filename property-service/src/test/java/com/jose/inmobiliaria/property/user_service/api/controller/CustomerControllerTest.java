package com.jose.inmobiliaria.property.user_service.api.controller;

import com.jose.inmobiliaria.property.common.security.JwtAuthenticationFilter;
import com.jose.inmobiliaria.property.common.security.JwtService;
import com.jose.inmobiliaria.property.user_service.application.service.CustomerService;
import com.jose.inmobiliaria.property.user_service.domain.entity.Customer;
import com.jose.inmobiliaria.property.user_service.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "test@mail.com")
    void shouldReturnAuthenticatedCustomer() throws Exception {

        // Crear User
        User user = new User();
        user.setEmail("test@mail.com");

        // Crear Customer y asociarlo
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Jose");
        customer.setLastName("Perez");
        customer.setUser(user);

        when(customerService.getCustomerByEmail("test@mail.com"))
                .thenReturn(customer);

        mockMvc.perform(get("/customers/me"))
                .andExpect(status().isOk());
    }
}