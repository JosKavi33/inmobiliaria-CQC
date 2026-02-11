package com.jose.inmobiliaria.property.service.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "property_images")
public class PropertyImage {

    /* ===============================
       IDENTIDAD
    =============================== */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ===============================
       DATOS DE LA IMAGEN
    =============================== */

    @NotBlank(message = "Image URL is required")
    @Column(nullable = false, length = 500)
    private String imageUrl;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer position; // orden: 0,1,2...

    /* ===============================
       RELACIÓN
    =============================== */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    /* ===============================
       CONSTRUCTOR
    =============================== */

    public PropertyImage() {
    }

    /* ===============================
       GETTERS & SETTERS
    =============================== */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
