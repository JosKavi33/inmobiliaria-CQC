package com.jose.inmobiliaria.property.service.infrastructure.specification;

import com.jose.inmobiliaria.property.service.domain.entity.Property;
import com.jose.inmobiliaria.property.service.domain.enums.OperationType;
import com.jose.inmobiliaria.property.service.domain.enums.PropertyType;
import org.springframework.data.jpa.domain.Specification;

public class PropertySpecifications {

    public static Specification<Property> hasPropertyType(PropertyType propertyType) {
        return (root, query, cb) ->
                propertyType == null ? null : cb.equal(root.get("propertyType"), propertyType);
    }

    public static Specification<Property> hasOperationType(OperationType operationType) {
        return (root, query, cb) ->
                operationType == null ? null : cb.equal(root.get("operationType"), operationType);
    }

    public static Specification<Property> hasCity(String city) {
        return (root, query, cb) ->
                city == null ? null : cb.equal(cb.lower(root.get("city")), city.toLowerCase());
    }

    public static Specification<Property> hasDepartment(String department) {
        return (root, query, cb) ->
                department == null ? null : cb.equal(cb.lower(root.get("department")), department.toLowerCase());
    }

    public static Specification<Property> priceBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null)
                return cb.between(root.get("price"), min, max);
            if (min != null)
                return cb.greaterThanOrEqualTo(root.get("price"), min);
            return cb.lessThanOrEqualTo(root.get("price"), max);
        };
    }

    public static Specification<Property> minBedrooms(Integer bedrooms) {
        return (root, query, cb) ->
                bedrooms == null ? null : cb.greaterThanOrEqualTo(root.get("bedrooms"), bedrooms);
    }

    public static Specification<Property> minBathrooms(Integer bathrooms) {
        return (root, query, cb) ->
                bathrooms == null ? null : cb.greaterThanOrEqualTo(root.get("bathrooms"), bathrooms);
    }

    public static Specification<Property> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

}
