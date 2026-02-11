package com.jose.inmobiliaria.property.service.infrastructure.repository;

import com.jose.inmobiliaria.property.service.domain.entity.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {

    // Para mostrar galería ordenada en la UI
    List<PropertyImage> findByPropertyIdOrderByPositionAsc(Long propertyId);

    // Útil si algún día quieres borrar imágenes sin borrar la propiedad
    void deleteByPropertyId(Long propertyId);
}
