package com.jose.inmobiliaria.property.service.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class PropertyImageRequestDTO {

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @PositiveOrZero
    private Integer position;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
