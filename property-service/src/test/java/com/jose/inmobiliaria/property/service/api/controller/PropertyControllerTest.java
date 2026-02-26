package com.jose.inmobiliaria.property.service.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jose.inmobiliaria.property.common.security.JwtAuthenticationFilter;
import com.jose.inmobiliaria.property.common.security.JwtService;
import com.jose.inmobiliaria.property.service.application.service.ImageService;
import com.jose.inmobiliaria.property.service.application.service.PropertyService;
import com.jose.inmobiliaria.property.service.domain.entity.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropertyController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(PropertyControllerTest.MethodSecurityTestConfig.class)
class PropertyControllerTest {

    @EnableMethodSecurity
    static class MethodSecurityTestConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private ImageService imageService;

    @Autowired
    private ObjectMapper objectMapper;

    /* ===============================
       GET ALL
    =============================== */

    @Test
    void shouldReturn200WhenGetProperties() throws Exception {

        when(propertyService.searchAllProperties(
                null, null, null, null,
                null, null, null, null,
                "id", "asc"
        )).thenReturn(java.util.List.of());

        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk());
    }

    /* ===============================
       GET BY ID
    =============================== */

    @Test
    void shouldReturnPropertyById() throws Exception {

        Property property = new Property();
        property.setId(1L);

        when(propertyService.getPropertyWithSignedUrls(1L))
                .thenReturn(property);

        mockMvc.perform(get("/properties/1"))
                .andExpect(status().isOk());
    }

    /* ===============================
       CREATE
    =============================== */

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreatePropertyWhenAdmin() throws Exception {

        Property property = new Property();
        property.setId(1L);

        when(propertyService.create(any()))
                .thenReturn(property);

        String json = """
                {
                    "title": "Casa Test",
                    "price": 100000,
                    "propertyType": "HOUSE",
                    "operationType": "SALE",
                    "address": "Calle 123",
                    "city": "Bogota",
                    "department": "Cundinamarca"
                }
                """;

        mockMvc.perform(post("/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn403WhenNotAdmin() throws Exception {

        String json = """
                {
                    "title": "Casa Test",
                    "price": 100000,
                    "propertyType": "HOUSE",
                    "operationType": "SALE",
                    "address": "Calle 123",
                    "city": "Bogota",
                    "department": "Cundinamarca"
                }
                """;

        mockMvc.perform(post("/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    /* ===============================
       DELETE
    =============================== */

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteProperty() throws Exception {

        doNothing().when(propertyService).deleteById(1L);

        mockMvc.perform(delete("/properties/1"))
                .andExpect(status().isOk());
    }
}