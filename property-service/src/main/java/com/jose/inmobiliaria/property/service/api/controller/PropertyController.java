package com.jose.inmobiliaria.property.service.api.controller;

import com.jose.inmobiliaria.property.common.mapper.PropertyMapper;
import com.jose.inmobiliaria.property.service.api.dto.request.PropertyCreateRequestDTO;
import com.jose.inmobiliaria.property.service.api.dto.response.ApiResponse;
import com.jose.inmobiliaria.property.service.api.dto.response.PageMeta;
import com.jose.inmobiliaria.property.service.api.dto.response.PropertyResponseDTO;
import com.jose.inmobiliaria.property.service.application.service.PropertyService;
import com.jose.inmobiliaria.property.service.domain.enums.OperationType;
import com.jose.inmobiliaria.property.service.domain.enums.PropertyType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /* ===============================
       GET (PAGINADO O COMPLETO)
    =============================== */
    @GetMapping
    public ResponseEntity<ApiResponse<?>> searchProperties(
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) OperationType operationType,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer bedrooms,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        // 🔹 Si vienen page y size → paginado
        if (page != null && size != null) {

            var pageResult = propertyService
                    .searchProperties(
                            propertyType, operationType,
                            city, department,
                            minPrice, maxPrice,
                            bedrooms, bathrooms,
                            page, size, sort, direction
                    )
                    .map(PropertyMapper::toResponse);

            ApiResponse<List<PropertyResponseDTO>> response =
                    new ApiResponse<>(
                            true,
                            "PROPERTIES_FOUND",
                            pageResult.getContent(),
                            new PageMeta(pageResult)
                    );

            return ResponseEntity.ok(response);
        }

        // 🔹 Si NO → trae todo
        var list = propertyService
                .searchAllProperties(
                        propertyType, operationType,
                        city, department,
                        minPrice, maxPrice,
                        bedrooms, bathrooms,
                        sort, direction
                )
                .stream()
                .map(PropertyMapper::toResponse)
                .toList();

        ApiResponse<List<PropertyResponseDTO>> response =
                new ApiResponse<>(
                        true,
                        "PROPERTIES_FOUND",
                        list
                );

        return ResponseEntity.ok(response);
    }


    /* ===============================
       GET BY ID
    =============================== */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PropertyResponseDTO>> getById(@PathVariable Long id) {

        var property = PropertyMapper.toResponse(
                propertyService.findByIdOrThrow(id)
        );

        ApiResponse<PropertyResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "PROPERTY_FOUND",
                        property
                );

        return ResponseEntity.ok(response);
    }


    /* ===============================
       CREATE
    =============================== */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<PropertyResponseDTO>> create(
            @Valid @RequestBody PropertyCreateRequestDTO requestDTO
    ) {

        var created = PropertyMapper.toResponse(
                propertyService.create(
                        PropertyMapper.toEntity(requestDTO)
                )
        );

        ApiResponse<PropertyResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "PROPERTY_CREATED",
                        created
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /* ===============================
       UPDATE
    =============================== */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PropertyResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody PropertyCreateRequestDTO requestDTO
    ) {

        var updated = PropertyMapper.toResponse(
                propertyService.update(id, PropertyMapper.toEntity(requestDTO))
        );

        ApiResponse<PropertyResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "PROPERTY_UPDATED",
                        updated
                );

        return ResponseEntity.ok(response);
    }


    /* ===============================
       DELETE
    =============================== */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {

        propertyService.deleteById(id);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        "PROPERTY_DELETED",
                        null
                );

        return ResponseEntity.ok(response);
    }
}
