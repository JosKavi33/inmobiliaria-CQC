package com.jose.inmobiliaria.property.service.infrastructure.repository;

import com.jose.inmobiliaria.property.service.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PropertyRepository extends JpaRepository<Property, Long>,
        JpaSpecificationExecutor<Property> {
}
