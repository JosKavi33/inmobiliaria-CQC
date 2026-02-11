import PropertyCard from "./PropertyCard";

function PropertyList({ properties, onEdit, onDelete }) {
  if (!properties.length) return <p>No hay propiedades registradas.</p>;
  

  return (
    <div
      style={{
  display: "grid",
  gridTemplateColumns: "repeat(auto-fit, 340px)",
  gap: 20,
  justifyContent: "space-evenly",
  padding: 20
}}
    >
      {properties.map((property) => (
        <PropertyCard key={property.id}
          property={property}
          onEdit={onEdit}
          onDelete={onDelete}/>
        ))}
    </div>
  );
}

export default PropertyList;
