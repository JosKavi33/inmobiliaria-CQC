import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8083",
});

export const getProperties = ({
  page = 0,
  size = 10,
  propertyType,
  operationType,
  city,
  department,
  minPrice,
  maxPrice,
  bedrooms,
  bathrooms,
  sort = "price",
  direction = "asc",
} = {}) =>
  api.get("/properties", {
    params: {
      page,
      size,
      propertyType,
      operationType,
      city,
      department,
      minPrice,
      maxPrice,
      bedrooms,
      bathrooms,
      sort,
      direction,
    },
  });


export const createProperty = (data) =>
  api.post("/properties", data);

export const updateProperty = (id, data) =>
  api.put(`/properties/${id}`, data);

export const deleteProperty = (id) =>
  api.delete(`/properties/${id}`);
