package com.jose.inmobiliaria.property.service.application.service;

import com.jose.inmobiliaria.property.service.infrastructure.config.SupabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private SupabaseConfig supabaseConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setup() {
        when(supabaseConfig.getUrl()).thenReturn("http://localhost:54321");
        when(supabaseConfig.getBucket()).thenReturn("bucket-test");
        when(supabaseConfig.getServiceRole()).thenReturn("service-role-key");
    }

    /* ==========================
       UPLOAD
    ========================== */

    @Test
    void shouldUploadImageSuccessfully() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "image.jpg",
                        "image/jpeg",
                        "fake-image".getBytes()
                );

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("OK"));

        String result = imageService.upload(file);

        assertNotNull(result);
        assertTrue(result.contains("image.jpg"));

        verify(restTemplate).exchange(
                contains("/object/bucket-test/"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(String.class)
        );
    }

    @Test
    void shouldThrowExceptionWhenUploadFails() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "image.jpg",
                        "image/jpeg",
                        "fake-image".getBytes()
                );

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(String.class)
        )).thenThrow(new RuntimeException("HTTP error"));

        assertThrows(RuntimeException.class,
                () -> imageService.upload(file));
    }

    /* ==========================
       GENERATE SIGNED URL
    ========================== */

    @Test
    void shouldGenerateSignedUrlSuccessfully() {

        String jsonResponse = "{ \"signedURL\": \"/object/sign/bucket-test/image.jpg?token=123\" }";

        when(restTemplate.postForObject(
                anyString(),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(jsonResponse);

        String result = imageService.generateSignedUrl("image.jpg");

        assertNotNull(result);
        assertTrue(result.contains("image.jpg"));
    }

    @Test
    void shouldThrowExceptionWhenSignedUrlMissing() {

        String jsonResponse = "{ \"wrongField\": \"value\" }";

        when(restTemplate.postForObject(
                anyString(),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(jsonResponse);

        assertThrows(RuntimeException.class,
                () -> imageService.generateSignedUrl("image.jpg"));
    }

    /* ==========================
       DELETE
    ========================== */

    @Test
    void shouldCallDeleteEndpointSuccessfully() {

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("OK"));

        imageService.delete("image.jpg");

        verify(restTemplate).exchange(
                contains("/object/bucket-test/image.jpg"),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(String.class)
        );
    }

    @Test
    void shouldNotThrowWhenDeleteFails() {

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(String.class)
        )).thenThrow(new RuntimeException("HTTP error"));

        assertDoesNotThrow(() ->
                imageService.delete("image.jpg"));
    }
}