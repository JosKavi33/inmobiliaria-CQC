import { useState } from "react";
import { deleteProperty } from "../api/propertyApi";

function PropertyCard({ property, onEdit, onDelete }) {
  const [showModal, setShowModal] = useState(false);
  const [currentImage, setCurrentImage] = useState(0);

  const images = property.images || [];
  const mainImage = images[0]?.imageUrl;

  const handlePrev = () =>
    setCurrentImage((prev) =>
      prev === 0 ? images.length - 1 : prev - 1
    );

  const handleNext = () =>
    setCurrentImage((prev) =>
      prev === images.length - 1 ? 0 : prev + 1
    );

  return (
    <>
      {/* Tarjeta */}
      <div
        style={{
          border: "1px solid #ccc",
          borderRadius: 8,
          overflow: "hidden",
          boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
          cursor: "pointer",
          width: "100%",
          maxWidth: "340px",
          transition: "transform 0.2s ease"
        }}
        onClick={() => setShowModal(true)}
      >
        {mainImage && (
          <img
            src={mainImage}
            alt={property.title}
            style={{ width: "100%", height: 200, objectFit: "cover" }}
          />
        )}

        <div style={{ padding: 15 }}>
          <h3
            style={{
              borderBottom: "1px solid #ccc",
              paddingBottom: 10
            }}
          >
            {property.title}
          </h3>

          <p>
            {property.operationType} |{" "}
            <strong>Precio:</strong> $
            {property.price.toLocaleString()}
          </p>

          <p>
            {property.city} - {property.department}
          </p>
        </div>
      </div>

      {/* Modal */}
      {showModal && (
        <div
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            backgroundColor: "rgba(0,0,0,0.7)",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            zIndex: 1000,
            padding: 20
          }}
          onClick={() => setShowModal(false)}
        >
          <div
            style={{
              backgroundColor: "#1f1f1f",
              borderRadius: 8,
              width: "90%",
              maxWidth: 900,
              maxHeight: "90%",
              overflowY: "auto",
              padding: 20,
              boxShadow: "0 5px 20px rgba(0,0,0,0.3)",
              position: "relative"
            }}
            onClick={(e) => e.stopPropagation()}
          >
            {/* Botón X arriba derecha */}
            <button
              onClick={() => setShowModal(false)}
              style={{
                position: "absolute",
                top: 15,
                right: 20,
                background: "transparent",
                border: "none",
                color: "white",
                fontSize: 20,
                cursor: "pointer",
                fontWeight: "bold"
              }}
            >
              ✕
            </button>

            <div
              style={{
                display: "flex",
                flexDirection: "column",
                gap: 15
              }}
            >
              {/* Información básica */}
              <div
                style={{
                  borderBottom: "1px solid #ccc",
                  paddingBottom: 10
                }}
              >
                <h2
                  style={{
                    borderBottom: "3px solid #ccc",
                    paddingBottom: 10
                  }}
                >
                  {property.title}
                </h2>

                <p>
                  <strong>Precio:</strong> $
                  {property.price.toLocaleString()}
                </p>

                <p>
                  {property.city} - {property.department}
                </p>

                <p>
                  <strong>Tipo:</strong> {property.propertyType} |{" "}
                  <strong>Operación:</strong> {property.operationType}
                </p>
              </div>

              {/* Dirección */}
              <div
                style={{
                  borderBottom: "1px solid #ccc",
                  paddingBottom: 10
                }}
              >
                <p>
                  <strong>Dirección:</strong> {property.address}
                </p>
                <p>
                  <strong>Barrio:</strong> {property.neighborhood}
                </p>
                <p>
                  <strong>Descripción:</strong>{" "}
                  {property.propertyDescription}
                </p>
                <p>
                  <strong>Ubicación detallada:</strong>{" "}
                  {property.locationDescription}
                </p>
              </div>

              {/* Características */}
              <div
                style={{
                  borderBottom: "1px solid #ccc",
                  paddingBottom: 10
                }}
              >
                <p>
                  <strong>Habitaciones:</strong> {property.bedrooms} |{" "}
                  <strong>Baños:</strong> {property.bathrooms} |{" "}
                  <strong>Parqueaderos:</strong>{" "}
                  {property.parkingSpaces}
                </p>

                <p>
                  <strong>Área lote:</strong> {property.lotArea} m² |{" "}
                  <strong>Área construida:</strong>{" "}
                  {property.builtArea} m²
                </p>
              </div>

              {/* Miniaturas */}
              {images.length > 0 && (
                <div
                  style={{
                    display: "flex",
                    overflowX: "auto",
                    gap: 5,
                    marginBottom: 15
                  }}
                >
                  {images.map((img, idx) => (
                    <img
                      key={idx}
                      src={img.imageUrl}
                      alt={`Imagen ${idx + 1}`}
                      style={{
                        width: 100,
                        height: 70,
                        objectFit: "cover",
                        border:
                          idx === currentImage
                            ? "2px solid #4da6ff"
                            : "1px solid #ccc",
                        borderRadius: 4,
                        cursor: "pointer"
                      }}
                      onClick={() => setCurrentImage(idx)}
                    />
                  ))}
                </div>
              )}

              {/* Imagen principal */}
              {images.length > 0 && (
                <div style={{ position: "relative" }}>
                  <img
                    src={images[currentImage].imageUrl}
                    alt={`Imagen ${currentImage + 1}`}
                    style={{
                      width: "100%",
                      maxHeight: 500,
                      objectFit: "contain",
                      borderRadius: 8
                    }}
                  />

                  <button
                    onClick={handlePrev}
                    style={{
                      position: "absolute",
                      top: "50%",
                      left: 10,
                      transform: "translateY(-50%)",
                      backgroundColor:
                        "rgba(0,0,0,0.5)",
                      color: "white",
                      border: "none",
                      borderRadius: "50%",
                      width: 40,
                      height: 40,
                      cursor: "pointer"
                    }}
                  >
                    ‹
                  </button>

                  <button
                    onClick={handleNext}
                    style={{
                      position: "absolute",
                      top: "50%",
                      right: 10,
                      transform: "translateY(-50%)",
                      backgroundColor:
                        "rgba(0,0,0,0.5)",
                      color: "white",
                      border: "none",
                      borderRadius: "50%",
                      width: 40,
                      height: 40,
                      cursor: "pointer"
                    }}
                  >
                    ›
                  </button>
                </div>
              )}

              {/* Botones Editar / Eliminar */}
              <div
                style={{
                  display: "flex",
                  justifyContent: "center",
                  gap: 15,
                  marginTop: 20
                }}
              >
                <button
                  onClick={() => onEdit(property)}
                  style={{
                    padding: "8px 14px",
                    backgroundColor: "#2f2f2f",
                    color: "#fff",
                    border: "none",
                    borderRadius: 6,
                    cursor: "pointer",
                    fontWeight: "bold"
                  }}
                >
                  Editar
                </button>

                <button
                  onClick={async () => {
                    if (
                      !confirm(
                        "¿Seguro quieres eliminar esta propiedad?"
                      )
                    )
                      return;

                    try {
                      await deleteProperty(property.id);
                      onDelete();
                      setShowModal(false);
                    } catch (err) {
                      console.error(err);
                      alert(
                        "Error eliminando propiedad"
                      );
                    }
                  }}
                  style={{
                    padding: "8px 14px",
                    backgroundColor: "#b22222",
                    color: "#fff",
                    border: "none",
                    borderRadius: 6,
                    cursor: "pointer",
                    fontWeight: "bold"
                  }}
                >
                  Eliminar
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default PropertyCard;
