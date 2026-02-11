package com.jose.inmobiliaria.property.service.api.dto.request;

import com.jose.inmobiliaria.property.service.domain.enums.OperationType;
import com.jose.inmobiliaria.property.service.domain.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

/**
 * DTO para crear una nueva propiedad
 * Se usa desde la API (Postman / Frontend)
 */
public class PropertyCreateRequestDTO {

    /* ===============================
       INFORMACIÓN GENERAL
    =============================== */

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Property type is required")
    private PropertyType propertyType;

    @NotNull(message = "Operation type is required")
    private OperationType operationType;

    @PositiveOrZero(message = "Administration fee must be zero or positive")
    private Double administrationFee;

    /* ===============================
       UBICACIÓN
    =============================== */

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Department is required")
    private String department;

    private String neighborhood;

    /* ===============================
       DESCRIPCIÓN
    =============================== */

    private String propertyDescription;
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
    private Integer lotArea;

    @PositiveOrZero
    private Integer builtArea;

    /* ===============================
       GETTERS & SETTERS
    =============================== */

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

    private List<com.jose.inmobiliaria.property.service.api.dto.request.PropertyImageRequestDTO> images;

    public List<com.jose.inmobiliaria.property.service.api.dto.request.PropertyImageRequestDTO> getImages() {
        return images;
    }

    public void setImages(List<com.jose.inmobiliaria.property.service.api.dto.request.PropertyImageRequestDTO> images) {
        this.images = images;
    }
}
