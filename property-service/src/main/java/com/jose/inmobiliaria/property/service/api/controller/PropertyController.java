package com.jose.inmobiliaria.property.service.api.controller;

import com.jose.inmobiliaria.property.service.api.dto.request.PropertyCreateRequestDTO;
import com.jose.inmobiliaria.property.service.api.dto.response.PropertyResponseDTO;
import com.jose.inmobiliaria.property.service.api.mapper.PropertyMapper;
import com.jose.inmobiliaria.property.service.application.service.PropertyService;
import com.jose.inmobiliaria.property.service.domain.enums.OperationType;
import com.jose.inmobiliaria.property.service.domain.enums.PropertyType;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /* ===============================
        GET ALL, SERCH
    =============================== */
    @GetMapping
    public ResponseEntity<Page<PropertyResponseDTO>> searchProperties(
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) OperationType operationType,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer bedrooms,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "price") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                propertyService.searchProperties(

                                propertyType,
                                operationType,
                                city,
                                department,
                                minPrice,
                                maxPrice,
                                bedrooms,
                                bathrooms,
                                page,
                                size,
                                sort,
                                direction
                        )
                        .map(PropertyMapper::toResponse)
        );
    }


    /* ===============================
       GET BY ID
    =============================== */
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                PropertyMapper.toResponse(
                        propertyService.findByIdOrThrow(id)
                )
        );
    }

    /* ===============================
       CREATE
    =============================== */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PropertyResponseDTO> create(
            @Valid @RequestBody PropertyCreateRequestDTO requestDTO
    ) {
        System.out.println("POST RECIBIDO");
        return ResponseEntity.ok(
                PropertyMapper.toResponse(
                        propertyService.create(
                                PropertyMapper.toEntity(requestDTO)
                        )
                )
        );
    }


    @PostMapping("/test")
    public ResponseEntity<String> testPost() {
        System.out.println("🚨 POST LLEGÓ AL CONTROLLER 🚨");
        return ResponseEntity.ok("OK");
    }


    /* ===============================
       UPDATE
    =============================== */
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PropertyCreateRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                PropertyMapper.toResponse(
                        propertyService.update(id, PropertyMapper.toEntity(requestDTO))
                )
        );
    }

    /* ===============================
       DELETE
    =============================== */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        propertyService.deleteById(id);
    }
}
