package com.jose.inmobiliaria.property.service.api.mapper;

import com.jose.inmobiliaria.property.service.api.dto.request.PropertyCreateRequestDTO;
import com.jose.inmobiliaria.property.service.api.dto.request.PropertyImageRequestDTO;
import com.jose.inmobiliaria.property.service.api.dto.response.PropertyImageResponseDTO;
import com.jose.inmobiliaria.property.service.api.dto.response.PropertyResponseDTO;
import com.jose.inmobiliaria.property.service.domain.entity.Property;
import com.jose.inmobiliaria.property.service.domain.entity.PropertyImage;

import java.util.ArrayList;
import java.util.List;

public class PropertyMapper {

    private PropertyMapper() {
    }

    /* ===============================
       REQUEST → ENTITY
    =============================== */
    public static Property toEntity(PropertyCreateRequestDTO dto) {
        Property property = new Property();

        property.setTitle(dto.getTitle());
        property.setPrice(dto.getPrice());
        property.setPropertyType(dto.getPropertyType());
        property.setOperationType(dto.getOperationType());
        property.setAdministrationFee(dto.getAdministrationFee());

        property.setAddress(dto.getAddress());
        property.setCity(dto.getCity());
        property.setDepartment(dto.getDepartment());
        property.setNeighborhood(dto.getNeighborhood());

        property.setPropertyDescription(dto.getPropertyDescription());
        property.setLocationDescription(dto.getLocationDescription());

        property.setBedrooms(dto.getBedrooms());
        property.setBathrooms(dto.getBathrooms());
        property.setParkingSpaces(dto.getParkingSpaces());
        property.setLotArea(dto.getLotArea());
        property.setBuiltArea(dto.getBuiltArea());

        property.setActive(true);

        // 🔹 IMÁGENES
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            List<PropertyImage> images = new ArrayList<>();
            for (PropertyImageRequestDTO imageDto : dto.getImages()) {
                PropertyImage image = new PropertyImage();
                image.setImageUrl(imageDto.getImageUrl());
                // NO setear posición ni property aquí
                images.add(image);
            }
            property.setImages(images);
        }

        return property;
    }

    /* ===============================
       ENTITY → RESPONSE
    =============================== */
    public static PropertyResponseDTO toResponse(Property property) {
        PropertyResponseDTO dto = new PropertyResponseDTO();

        dto.setId(property.getId());
        dto.setTitle(property.getTitle());
        dto.setPrice(property.getPrice());
        dto.setPropertyType(property.getPropertyType());
        dto.setOperationType(property.getOperationType());
        dto.setActive(property.getActive());
        dto.setAdministrationFee(property.getAdministrationFee());

        dto.setAddress(property.getAddress());
        dto.setCity(property.getCity());
        dto.setDepartment(property.getDepartment());
        dto.setNeighborhood(property.getNeighborhood());

        dto.setPropertyDescription(property.getPropertyDescription());
        dto.setLocationDescription(property.getLocationDescription());

        dto.setBedrooms(property.getBedrooms());
        dto.setBathrooms(property.getBathrooms());
        dto.setParkingSpaces(property.getParkingSpaces());
        dto.setLotArea(property.getLotArea());
        dto.setBuiltArea(property.getBuiltArea());

        // 🔹 IMÁGENES
        if (property.getImages() != null && !property.getImages().isEmpty()) {
            List<PropertyImageResponseDTO> images = new ArrayList<>();
            for (PropertyImage image : property.getImages()) {
                PropertyImageResponseDTO imageDto = new PropertyImageResponseDTO();
                imageDto.setId(image.getId());
                imageDto.setImageUrl(image.getImageUrl());
                imageDto.setPosition(image.getPosition());
                images.add(imageDto);
            }
            dto.setImages(images);
        }

        return dto;
    }
}
