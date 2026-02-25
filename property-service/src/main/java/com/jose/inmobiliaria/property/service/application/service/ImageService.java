package com.jose.inmobiliaria.property.service.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jose.inmobiliaria.property.service.infrastructure.config.SupabaseConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final SupabaseConfig supabaseConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String getBaseStorageUrl() {
        return supabaseConfig.getUrl() + "/storage/v1";
    }

    public String upload(MultipartFile file) {

        log.info("Starting image upload: name={}, size={}",
                file.getOriginalFilename(),
                file.getSize());

        try {

            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            String uploadUrl = getBaseStorageUrl()
                    + "/object/"
                    + supabaseConfig.getBucket()
                    + "/"
                    + fileName;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(supabaseConfig.getServiceRole());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            HttpEntity<byte[]> request =
                    new HttpEntity<>(file.getBytes(), headers);

            restTemplate.exchange(uploadUrl, HttpMethod.PUT, request, String.class);

            log.info("Image uploaded successfully: {}", fileName);

            return fileName;

        } catch (Exception e) {

            log.error("Error uploading image: {}", file.getOriginalFilename(), e);

            throw new RuntimeException("Image upload failed", e);
        }
    }


    public String generateSignedUrl(String filePath) {

        log.debug("Generating signed URL for: {}", filePath);

        String url = getBaseStorageUrl()
                + "/object/sign/"
                + supabaseConfig.getBucket()
                + "/"
                + filePath;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(supabaseConfig.getServiceRole());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{ \"expiresIn\": 3600 }";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        String response = restTemplate.postForObject(url, request, String.class);

        try {
            JsonNode node = objectMapper.readTree(response);

            if (!node.has("signedURL")) {
                throw new RuntimeException("Supabase no devolvió signedURL");
            }

            String signedPath = node.get("signedURL").asText();

            return getBaseStorageUrl() + signedPath;

        } catch (Exception e) {
            throw new RuntimeException("Error generando signed URL", e);
        }
    }

    public void delete(String filePath) {

        log.info("Deleting image from bucket: {}", filePath);

        try {

            String deleteUrl = getBaseStorageUrl()
                    + "/object/"
                    + supabaseConfig.getBucket()
                    + "/"
                    + filePath;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(supabaseConfig.getServiceRole());

            HttpEntity<Void> request = new HttpEntity<>(headers);

            restTemplate.exchange(deleteUrl, HttpMethod.DELETE, request, String.class);

            log.info("Image deleted successfully: {}", filePath);

        } catch (Exception e) {

            log.error("Error deleting image: {}", filePath, e);
        }
    }
}