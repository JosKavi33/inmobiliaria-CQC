package com.jose.inmobiliaria.property.service.domain.entity;

import com.jose.inmobiliaria.property.service.domain.enums.OperationType;
import com.jose.inmobiliaria.property.service.domain.enums.PropertyType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
public class Property {

    /* ===============================
       RELACIÓN CON IMÁGENES
    =============================== */

    @OneToMany(
            mappedBy = "property",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<PropertyImage> images = new ArrayList<>();

    /* ===============================
       IDENTIDAD
    =============================== */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ===============================
       INFORMACIÓN GENERAL
    =============================== */

    @NotBlank(message = "Title is required")
    @Column(length = 500)
    private String title;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Property type is required")
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @NotNull(message = "Operation type is required")
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private Double administrationFee;

    /* ===============================
       UBICACIÓN
    =============================== */

    @NotBlank(message = "Address is required")
    @Column(columnDefinition = "TEXT")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Department is required")
    private String department;

    @Column(columnDefinition = "TEXT")
    private String neighborhood;

    /* ===============================
       DESCRIPCIÓN
    =============================== */

    @Column(columnDefinition = "TEXT")
    private String propertyDescription;

    @Column(columnDefinition = "TEXT")
    private String locationDescription;

    /* ===============================
       CARACTERÍSTICAS
    =============================== */

    @PositiveOrZero
    private Integer bedrooms;

    @PositiveOrZero
    private Integer bathrooms;

    @PositiveOrZero
    private Integer parkingSpaces;

    @PositiveOrZero
    private Integer lotArea;      // m²

    @PositiveOrZero
    private Integer builtArea;    // m²

    /* ===============================
       ESTADO (PUBLICACIÓN)
    =============================== */

    @NotNull
    private Boolean active = true;

    /* ===============================
       CONSTRUCTOR
    =============================== */

    public Property() {
    }

    /* ===============================
       MÉTODOS DE RELACIÓN (IMPORTANTE)
    =============================== */

    public void addImage(PropertyImage image) {
        images.add(image);
        image.setProperty(this);
    }

    public void removeImage(PropertyImage image) {
        images.remove(image);
        image.setProperty(null);
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

    public List<PropertyImage> getImages() {
        return images;
    }

    public void setImages(List<PropertyImage> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Double getAdministrationFee() {
        return administrationFee;
    }

    public void setAdministrationFee(Double administrationFee) {
        this.administrationFee = administrationFee;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(Integer parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public Integer getLotArea() {
        return lotArea;
    }

    public void setLotArea(Integer lotArea) {
        this.lotArea = lotArea;
    }

    public Integer getBuiltArea() {
        return builtArea;
    }

    public void setBuiltArea(Integer builtArea) {
        this.builtArea = builtArea;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
