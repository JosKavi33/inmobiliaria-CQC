package com.jose.inmobiliaria.property.service.application.service;

import com.jose.inmobiliaria.property.service.api.exception.ResourceNotFoundException;
import com.jose.inmobiliaria.property.service.domain.entity.Property;
import com.jose.inmobiliaria.property.service.domain.entity.PropertyImage;
import com.jose.inmobiliaria.property.service.domain.enums.OperationType;
import com.jose.inmobiliaria.property.service.domain.enums.PropertyType;
import com.jose.inmobiliaria.property.service.infrastructure.repository.PropertyRepository;
import com.jose.inmobiliaria.property.service.infrastructure.specification.PropertySpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /* ===============================
       READ
    =============================== */

    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    public Property findByIdOrThrow(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Property with id " + id + " not found"
                        )
                );
    }

    /* ===============================
       CREATE
    =============================== */

    public Property create(Property property) {

        // Validación básica de imágenes
        if (property.getImages() != null && property.getImages().size() > 10) {
            throw new IllegalArgumentException("A property can have a maximum of 10 images");
        }

        // Setear relación bidireccional + posición
        if (property.getImages() != null) {
            int position = 0;
            for (PropertyImage image : property.getImages()) {
                image.setProperty(property);
                image.setPosition(position++);
            }
        }

        return propertyRepository.save(property);
    }


    /* ===============================
       UPDATE
    =============================== */

    public Property update(Long id, Property updated) {
        Property existing = findByIdOrThrow(id);

        // ===============================
        // ACTUALIZAR DATOS GENERALES
        // ===============================
        existing.setTitle(updated.getTitle());
        existing.setPrice(updated.getPrice());
        existing.setPropertyType(updated.getPropertyType());
        existing.setOperationType(updated.getOperationType());
        existing.setAdministrationFee(updated.getAdministrationFee());

        existing.setAddress(updated.getAddress());
        existing.setCity(updated.getCity());
        existing.setDepartment(updated.getDepartment());
        existing.setNeighborhood(updated.getNeighborhood());

        existing.setPropertyDescription(updated.getPropertyDescription());
        existing.setLocationDescription(updated.getLocationDescription());

        existing.setBedrooms(updated.getBedrooms());
        existing.setBathrooms(updated.getBathrooms());
        existing.setParkingSpaces(updated.getParkingSpaces());
        existing.setLotArea(updated.getLotArea());
        existing.setBuiltArea(updated.getBuiltArea());

        // ===============================
        // ACTUALIZAR IMÁGENES
        // ===============================
        if (updated.getImages() != null) {
            if (updated.getImages().size() > 10) {
                throw new IllegalArgumentException("A property can have a maximum of 10 images");
            }

            // Limpiar imágenes existentes
            existing.getImages().clear();

            // Setear nueva lista con posición y relación
            int position = 0;
            for (PropertyImage image : updated.getImages()) {
                image.setProperty(existing);
                image.setPosition(position++);
                existing.getImages().add(image);
            }
        }

        return propertyRepository.save(existing);
    }


    /* ===============================
       DELETE
    =============================== */

    public void deleteById(Long id) {
        Property property = findByIdOrThrow(id);
        propertyRepository.delete(property);
    }

    /* ===============================
       SEARCH + PAGINATION
    =============================== */

    public Page<Property> searchProperties(
            PropertyType propertyType,
            OperationType operationType,
            String city,
            String department,
            Double minPrice,
            Double maxPrice,
            Integer bedrooms,
            Integer bathrooms,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        // Validaciones
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
        }

        if (page < 0) {
            throw new IllegalArgumentException("page must be >= 0");
        }

        if (size <= 0 || size > 50) {
            throw new IllegalArgumentException("size must be between 1 and 50");
        }

        String safeSort = switch (sortBy) {
            case "price", "bedrooms", "bathrooms" -> sortBy;
            default -> "price";
        };

        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(safeSort).descending().and(Sort.by("id").descending())
                : Sort.by(safeSort).ascending().and(Sort.by("id").ascending());

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Property> spec = (root, query, cb) -> cb.conjunction();

        spec = spec.and(PropertySpecifications.isActive());
        spec = spec.and(PropertySpecifications.hasPropertyType(propertyType));
        spec = spec.and(PropertySpecifications.hasOperationType(operationType));
        spec = spec.and(PropertySpecifications.hasCity(city));
        spec = spec.and(PropertySpecifications.hasDepartment(department));
        spec = spec.and(PropertySpecifications.priceBetween(minPrice, maxPrice));
        spec = spec.and(PropertySpecifications.minBedrooms(bedrooms));
        spec = spec.and(PropertySpecifications.minBathrooms(bathrooms));


        return propertyRepository.findAll(spec, pageable);
    }
}

