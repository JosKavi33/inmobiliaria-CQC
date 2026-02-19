import { useEffect, useState } from "react";
import PropertyCard from "./PropertyCard";

function PropertyList({ onEdit, onDelete }) {
  const [properties, setProperties] = useState([]); // siempre array
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Función para traer propiedades desde tu API
  const fetchProperties = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch("http://localhost:8083/properties"); // ajusta URL si cambia
      if (!response.ok) {
        throw new Error(`Error al cargar propiedades: ${response.status}`);
      }
      const data = await response.json();
      // data debe venir con tu estructura ApiResponse { success, message, data, pageMeta? }
      if (data && data.data) {
        setProperties(data.data);
      } else {
        setProperties([]);
      }
    } catch (err) {
      console.error(err);
      setError(err.message || "Error desconocido");
      setProperties([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProperties();
  }, []);

  if (loading) return <p>Cargando propiedades...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!properties.length) return <p>No hay propiedades registradas.</p>;

  return (
    <div
      style={{
        display: "grid",
        gridTemplateColumns: "repeat(auto-fit, 340px)",
        gap: 20,
        justifyContent: "space-evenly",
        padding: 20,
      }}
    >
      {properties.map((property) => (
        <PropertyCard
          key={property.id}
          property={property}
          onEdit={onEdit}
          onDelete={onDelete}
        />
      ))}
    </div>
  );
}

export default PropertyList;
