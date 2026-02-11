import { useState, useEffect } from "react";
import { getProperties } from "./api/propertyApi";
import PropertyList from "./components/PropertyList";
import AddPropertyModal from "./components/AddPropertyModal";

export default function App() {

  const [properties, setProperties] = useState([]);
  const [editingProperty, setEditingProperty] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Paginación
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(3);
  const [totalPages, setTotalPages] = useState(0);

  // Borrar
  const handleDelete = () => {
    loadProperties();
  };

  // Filtros
  const [filters, setFilters] = useState({
    propertyType: "",
    operationType: "",
    city: "",
    department: "",
    minPrice: "",
    maxPrice: "",
    bedrooms: "",
    bathrooms: ""
  });

  const handleFilterChange = (e) => {
    setFilters({
      ...filters,
      [e.target.name]: e.target.value
    });
  };

  // 🔥 Carga principal
  const loadProperties = (activeFilters = filters) => {
    setLoading(true);
    setError(null); // 🔥 limpiamos error antes de cargar

    const cleanedFilters = Object.fromEntries(
      Object.entries(activeFilters).filter(
        ([_, value]) => value !== "" && value !== null
      )
    );

    getProperties({
      page,
      size,
      ...cleanedFilters
    })
      .then(res => {
        setProperties(res.data.content);
        setTotalPages(res.data.totalPages);
      })
      .catch(err => {
        console.error(err);
        setError("Error cargando propiedades");
      })
      .finally(() => setLoading(false));
  };

  // 🔥 Solo depende de page y size
  useEffect(() => {
    loadProperties();
  }, [page, size]);

  return (
    <div
      style={{
        width: "100vw",
        minHeight: "100vh",
        padding: 20,
        boxSizing: "border-box"
      }}
    >
      <h1 style={{ borderBottom: "5px solid #ccc", paddingBottom: 5 }}>
        Inmobiliaria Cabrejo Quinta Casas
      </h1>

      <h2>Inmuebles Venta</h2>

      {/* 🔥 FILTROS */}
      <div
        style={{
          display: "flex",
          gap: 10,
          marginBottom: 20,
          flexWrap: "wrap"
        }}
      >
        <input
          name="city"
          placeholder="Ciudad"
          value={filters.city}
          onChange={handleFilterChange}
        />

        <input
          name="minPrice"
          type="number"
          placeholder="Precio mínimo"
          value={filters.minPrice}
          onChange={handleFilterChange}
        />

        <input
          name="maxPrice"
          type="number"
          placeholder="Precio máximo"
          value={filters.maxPrice}
          onChange={handleFilterChange}
        />

        <button
          onClick={() => {
            setPage(0);
            loadProperties(filters);
          }}
        >
          Buscar
        </button>

        <button
          onClick={() => {
            const emptyFilters = {
              propertyType: "",
              operationType: "",
              city: "",
              department: "",
              minPrice: "",
              maxPrice: "",
              bedrooms: "",
              bathrooms: ""
            };
            setFilters(emptyFilters);
            setPage(0);
            loadProperties(emptyFilters);
          }}
        >
          Limpiar
        </button>
      </div>

      {/* 🔥 ERROR */}
      {error && (
        <div
          style={{
            backgroundColor: "#ffdddd",
            padding: 10,
            marginBottom: 15,
            borderRadius: 6,
            textAlign: "center"
          }}
        >
          {error}
        </div>
      )}

      {/* 🔥 LOADING */}
      {loading && (
        <p style={{ textAlign: "center", marginTop: 20 }}>
          Cargando propiedades...
        </p>
      )}

      {/* 🔥 LISTA */}
      {!loading && !error && (
        <PropertyList
          properties={properties}
          onEdit={(property) => {
            setEditingProperty(property);
            setShowModal(true);
          }}
          onDelete={handleDelete}
        />
      )}

      {/* BOTÓN AGREGAR */}
      <div
        style={{
          marginTop: 40,
          display: "flex",
          justifyContent: "center"
        }}
      >
        <button
          onClick={() => setShowModal(true)}
          style={{
            padding: "10px 15px",
            backgroundColor: "#2f2f2f",
            color: "#fff",
            border: "none",
            borderRadius: 6,
            cursor: "pointer",
            fontWeight: "bold",
            marginBottom: 10
          }}
        >
          + Agregar Propiedad
        </button>
      </div>

      <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        gap: 10,
        marginTop: 20
      }}>
  
        <label>Propiedades por página:</label>

      <select
        value={size}
        onChange={(e) => {
          setPage(0); // vuelve a la página 1
          setSize(Number(e.target.value));
        }}
        style={{
          padding: 5,
          borderRadius: 5
        }}>
        <option value={3}>3</option>
        <option value={6}>6</option>
        <option value={9}>9</option>
        <option value={12}>12</option>
      </select>
    </div>  

      {/* 🔥 PAGINACIÓN */}
      <div
        style={{
          marginTop: 20,
          display: "flex",
          justifyContent: "center"
        }}
      >
        <div
          style={{
            display: "flex",
            alignItems: "center",
            gap: 20,
            backgroundColor: "#1f1f1f",
            padding: "10px 20px",
            borderRadius: 8
          }}
        >
          <button
            disabled={page === 0}
            onClick={() => setPage(prev => prev - 1)}
          >
            Anterior
          </button>

          <span>
            Página {page + 1} de {totalPages}
          </span>

          <button
            disabled={page + 1 === totalPages}
            onClick={() => setPage(prev => prev + 1)}
          >
            Siguiente
          </button>
        </div>
      </div>

      {/* 🔥 MODAL */}
      <AddPropertyModal
        isOpen={showModal}
        propertyToEdit={editingProperty}
        onClose={() => {
          setShowModal(false);
          setEditingProperty(null);
          loadProperties();
        }}
      />
    </div>
  );
}
