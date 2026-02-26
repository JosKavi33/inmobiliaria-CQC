package com.jose.inmobiliaria.property.service.application.service;

import com.jose.inmobiliaria.property.common.exception.ResourceNotFoundException;
import com.jose.inmobiliaria.property.service.domain.entity.Property;
import com.jose.inmobiliaria.property.service.domain.entity.PropertyImage;
import com.jose.inmobiliaria.property.service.infrastructure.repository.PropertyImageRepository;
import com.jose.inmobiliaria.property.service.infrastructure.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private PropertyImageRepository propertyImageRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property property;

    @BeforeEach
    void setup() {
        property = new Property();
        property.setId(1L);
        property.setTitle("Casa Test");
        property.setImages(new ArrayList<>());
    }

    /* ===============================
       CREATE
    =============================== */

    @Test
    void shouldCreatePropertySuccessfully() {

        when(propertyRepository.save(any(Property.class)))
                .thenReturn(property);

        Property result = propertyService.create(property);

        assertNotNull(result);
        verify(propertyRepository).save(property);
    }

    @Test
    void shouldThrowExceptionWhenMoreThan10Images() {

        List<PropertyImage> images = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            images.add(new PropertyImage());
        }
        property.setImages(images);

        assertThrows(IllegalArgumentException.class,
                () -> propertyService.create(property));
    }

    /* ===============================
       UPDATE
    =============================== */

    @Test
    void shouldUpdatePropertySuccessfully() {

        Property updated = new Property();
        updated.setTitle("Updated");

        when(propertyRepository.findById(1L))
                .thenReturn(Optional.of(property));

        when(propertyRepository.save(any(Property.class)))
                .thenReturn(property);

        Property result = propertyService.update(1L, updated);

        assertEquals("Updated", result.getTitle());
        verify(propertyRepository).save(property);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingProperty() {

        when(propertyRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> propertyService.update(99L, new Property()));
    }

    /* ===============================
       DELETE
    =============================== */

    @Test
    void shouldDeletePropertySuccessfully() {

        property.setImages(new ArrayList<>());

        when(propertyRepository.findById(1L))
                .thenReturn(Optional.of(property));

        propertyService.deleteById(1L);

        verify(propertyRepository).delete(property);
    }

    @Test
    void shouldCallImageServiceWhenDeletingPropertyWithImages() {

        PropertyImage image = new PropertyImage();
        image.setImagePath("img.jpg");

        property.setImages(List.of(image));

        when(propertyRepository.findById(1L))
                .thenReturn(Optional.of(property));

        propertyService.deleteById(1L);

        verify(imageService).delete("img.jpg");
        verify(propertyRepository).delete(property);
    }

    /* ===============================
       ADD IMAGE
    =============================== */

    @Test
    void shouldAddImageToProperty() {

        property.setImages(new ArrayList<>());

        when(propertyRepository.findById(1L))
                .thenReturn(Optional.of(property));

        propertyService.addImageToProperty(1L, "file.jpg");

        verify(propertyRepository).save(property);
        assertEquals(1, property.getImages().size());
    }

    @Test
    void shouldThrowWhenPropertyNotFoundOnAddImage() {

        when(propertyRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> propertyService.addImageToProperty(99L, "file.jpg"));
    }

    /* ===============================
       DELETE IMAGE
    =============================== */

    @Test
    void shouldDeleteImageSuccessfully() {

        PropertyImage image = new PropertyImage();
        image.setId(10L);
        image.setImagePath("img.jpg");
        image.setProperty(property);

        when(propertyImageRepository.findById(10L))
                .thenReturn(Optional.of(image));

        when(propertyImageRepository.findByPropertyIdOrderByPositionAsc(1L))
                .thenReturn(List.of());

        propertyService.deleteImage(10L);

        verify(imageService).delete("img.jpg");
        verify(propertyImageRepository).delete(image);
    }
}