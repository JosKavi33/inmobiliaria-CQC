package com.jose.inmobiliaria.property.service.api.dto.response;

import com.jose.inmobiliaria.property.service.domain.enums.OperationType;
import com.jose.inmobiliaria.property.service.domain.enums.PropertyType;

import java.util.List;

/**
 * DTO de salida para exponer la información de una propiedad
 * Se usa para respuestas de la API (GET)
 */
public class PropertyResponseDTO {

    private Long id;

    /* ===============================
       INFORMACIÓN GENERAL
    =============================== */

    private String title;
    private Double price;
    private PropertyType propertyType;
    private OperationType operationType;
    private Boolean active;
    private Double administrationFee;

    /* ===============================
       UBICACIÓN
    =============================== */

    private String address;
    private String city;
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

    private Integer bedrooms;
    private Integer bathrooms;
    private Integer parkingSpaces;
    private Integer lotArea;
    private Integer builtArea;

    /* ===============================
       GETTERS & SETTERS
    =============================== */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    private List<com.jose.inmobiliaria.property.service.api.dto.response.PropertyImageResponseDTO> images;

    public List<com.jose.inmobiliaria.property.service.api.dto.response.PropertyImageResponseDTO> getImages() {
        return images;
    }

    public void setImages(List<com.jose.inmobiliaria.property.service.api.dto.response.PropertyImageResponseDTO> images) {
        this.images = images;
    }

}
