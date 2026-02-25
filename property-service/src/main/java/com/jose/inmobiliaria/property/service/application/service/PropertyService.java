package com.jose.inmobiliaria.property.service.application.service;

import com.jose.inmobiliaria.property.common.exception.ResourceNotFoundException;
import com.jose.inmobiliaria.property.service.domain.entity.Property;
import com.jose.inmobiliaria.property.service.domain.entity.PropertyImage;
import com.jose.inmobiliaria.property.service.domain.enums.OperationType;
import com.jose.inmobiliaria.property.service.domain.enums.PropertyType;
import com.jose.inmobiliaria.property.service.infrastructure.repository.PropertyImageRepository;
import com.jose.inmobiliaria.property.service.infrastructure.repository.PropertyRepository;
import com.jose.inmobiliaria.property.service.infrastructure.specification.PropertySpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final ImageService imageService;
    private final PropertyImageRepository propertyImageRepository;

    public PropertyService(PropertyRepository propertyRepository,
                           ImageService imageService, PropertyImageRepository propertyImageRepository) {
        this.propertyRepository = propertyRepository;
        this.imageService = imageService;
        this.propertyImageRepository = propertyImageRepository;
    }

    /* ===============================
       GET BY ID (CON SIGNED URL)
    =============================== */

    @Transactional(readOnly = true)
    public Property getPropertyWithSignedUrls(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Property with id " + id + " not found"
                        )
                );

        generateSignedUrlsSafely(property);

        return property;
    }

    /* ===============================
       CREATE
    =============================== */

    @Transactional
    public Property create(Property property) {

        log.info("Creating property: title={}", property.getTitle());

        if (property.getImages() != null) {

            if (property.getImages().size() > 10) {
                throw new IllegalArgumentException(
                        "A property can have a maximum of 10 images"
                );
            }

            int position = 0;
            for (PropertyImage image : property.getImages()) {
                image.setProperty(property);
                image.setPosition(position++);
            }
        }

        Property saved = propertyRepository.save(property);

        log.info("Property created successfully with id={}", saved.getId());

        return saved;
    }

    /* ===============================
       ADD IMAGE
    =============================== */

    @Transactional
    public void addImageToProperty(Long propertyId, String filePath) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Property with id " + propertyId + " not found"
                        )
                );

        if (property.getImages().size() >= 10) {
            throw new IllegalArgumentException(
                    "A property can have a maximum of 10 images"
            );
        }

        PropertyImage image = new PropertyImage();
        image.setImagePath(filePath);
        image.setPosition(property.getImages().size());
        image.setProperty(property);

        property.getImages().add(image);

        propertyRepository.save(property);
    }

    /* ===============================
       UPDATE
    =============================== */

    @Transactional
    public Property update(Long id, Property updated) {

        log.info("Updating property id={}", id);

        Property existing = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Property with id " + id + " not found"
                        )
                );

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

        log.info("Updating property id={}", id);

        return propertyRepository.save(existing);
    }

    /* ===============================
       DELETE
    =============================== */

    @Transactional
    public void deleteById(Long id) {

        log.info("Deleting property id={}", id);

        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Property with id " + id + " not found"
                        )
                );

        if (property.getImages() != null) {
            property.getImages().forEach(image -> {
                if (image.getImagePath() != null) {
                    try {
                        imageService.delete(image.getImagePath());
                    } catch (Exception ignored) {
                    }
                }
            });
        }

        propertyRepository.delete(property);
        log.info("Property deleted successfully id={}", id);
    }

    /* ===============================
       IMAGE DELETE
    =============================== */

    @Transactional
    public void deleteImage(Long imageId) {

        PropertyImage image = propertyImageRepository.findById(imageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Image not found")
                );

        Long propertyId = image.getProperty().getId();

        if (image.getImagePath() != null) {
            try {
                imageService.delete(image.getImagePath());
            } catch (Exception ignored) {
            }
        }

        propertyImageRepository.delete(image);

        List<PropertyImage> images =
                propertyImageRepository.findByPropertyIdOrderByPositionAsc(propertyId);

        int position = 0;
        for (PropertyImage img : images) {
            img.setPosition(position++);
        }
    }

    /* ===============================
       SEARCH PAGINADO
    =============================== */

    @Transactional(readOnly = true)
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

        String safeSort = switch (sortBy) {
            case "price", "bedrooms", "bathrooms", "id" -> sortBy;
            default -> "id";
        };

        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(safeSort).descending()
                : Sort.by(safeSort).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Property> spec =
                PropertySpecifications.isActive()
                        .and(PropertySpecifications.hasPropertyType(propertyType))
                        .and(PropertySpecifications.hasOperationType(operationType))
                        .and(PropertySpecifications.hasCity(city))
                        .and(PropertySpecifications.hasDepartment(department))
                        .and(PropertySpecifications.priceBetween(minPrice, maxPrice))
                        .and(PropertySpecifications.minBedrooms(bedrooms))
                        .and(PropertySpecifications.minBathrooms(bathrooms));

        Page<Property> result = propertyRepository.findAll(spec, pageable);

        result.forEach(this::generateSignedUrlsSafely);

        return result;
    }

    /* ===============================
       SEARCH SIN PAGINAR
    =============================== */

    @Transactional(readOnly = true)
    public List<Property> searchAllProperties(
            PropertyType propertyType,
            OperationType operationType,
            String city,
            String department,
            Double minPrice,
            Double maxPrice,
            Integer bedrooms,
            Integer bathrooms,
            String sortBy,
            String direction
    ) {

        log.debug("Searching properties with filters...");

        String safeSort = switch (sortBy) {
            case "price", "bedrooms", "bathrooms", "id" -> sortBy;
            default -> "id";
        };

        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(safeSort).descending()
                : Sort.by(safeSort).ascending();

        Specification<Property> spec =
                PropertySpecifications.isActive()
                        .and(PropertySpecifications.hasPropertyType(propertyType))
                        .and(PropertySpecifications.hasOperationType(operationType))
                        .and(PropertySpecifications.hasCity(city))
                        .and(PropertySpecifications.hasDepartment(department))
                        .and(PropertySpecifications.priceBetween(minPrice, maxPrice))
                        .and(PropertySpecifications.minBedrooms(bedrooms))
                        .and(PropertySpecifications.minBathrooms(bathrooms));

        List<Property> properties = propertyRepository.findAll(spec, sort);

        properties.forEach(this::generateSignedUrlsSafely);

        log.info("Properties found: {}", properties.size());

        return properties;
    }

    /* ===============================
       PRIVATE
    =============================== */

    private void generateSignedUrlsSafely(Property property) {

        if (property.getImages() == null) return;

        for (PropertyImage image : property.getImages()) {

            if (image.getImagePath() == null) continue;

            try {
                String signedUrl =
                        imageService.generateSignedUrl(image.getImagePath());

                image.setImagePath(signedUrl);

            } catch (Exception ignored) {
                // Si falla, no rompemos el GET
            }
        }
    }
}