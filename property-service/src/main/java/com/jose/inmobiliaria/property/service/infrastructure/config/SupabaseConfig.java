package com.jose.inmobiliaria.property.service.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
@Getter
public class SupabaseConfig {

    @Value("${supabase.url}")
    private String url;

    @Value("${supabase.service-role}")
    private String serviceRole;

    @Value("${supabase.bucket}")
    private String bucket;

}