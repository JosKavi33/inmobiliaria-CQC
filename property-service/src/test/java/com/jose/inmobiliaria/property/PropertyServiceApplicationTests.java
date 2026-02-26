package com.jose.inmobiliaria.property;

import com.jose.inmobiliaria.property.service.application.service.ImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PropertyServiceApplicationTests {

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ImageService imageService() {
            return Mockito.mock(ImageService.class);
        }
    }

    @Test
    void contextLoads() {
    }
}