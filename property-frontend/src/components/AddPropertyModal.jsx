import { useState, useEffect } from "react";
import { createProperty, updateProperty } from "../api/propertyApi";
import { supabase } from "../supabaseClient";

const PROPERTY_TYPES = ["APARTMENT","HOUSE","LOT","FARM","RURAL_HOUSE"];
const OPERATION_TYPES = ["SALE","RENT","PERMUTATION","N/A"];

export default function AddPropertyModal({  isOpen, onClose, propertyToEdit }) {
  const initialState = {
    title: "",
    price: "",
    propertyType: "LOT",
    operationType: "SALE",
    administrationFee: "",
    address: "",
    city: "",
    department: "",
    neighborhood: "",
    propertyDescription: "",
    locationDescription: "",
    bedrooms: "",
    bathrooms: "",
    parkingSpaces: "",
    lotArea: "",
    builtArea: "",
  };

  const [formData, setFormData] = useState(initialState);

  useEffect(() => {
    if (propertyToEdit) {
      setFormData({
      ...propertyToEdit,
      price: propertyToEdit.price || "",
      administrationFee: propertyToEdit.administrationFee || "",
    });
    } else {
      setFormData(initialState);
    }
  }, [propertyToEdit]);

  const [images, setImages] = useState([]);
  const [imagePreviews, setImagePreviews] = useState([]);
  const [uploading, setUploading] = useState(false);

  const isLot = formData.propertyType === "LOT";

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleImagesChange = (e) => {
    const files = Array.from(e.target.files);
    setImages(files);
    setImagePreviews(files.map(f => URL.createObjectURL(f)));
  };

  const uploadImages = async () => {
    const uploaded = [];
    for (let i = 0; i < images.length; i++) {
      const file = images[i];
      const fileExt = file.name.split(".").pop();
      const fileName = `${Date.now()}_${i}.${fileExt}`;

      const { data, error } = await supabase.storage
        .from("property-images")
        .upload(fileName, file);

      if (error) throw error;

      const { data: publicUrlData } = supabase
        .storage
        .from("property-images")
        .getPublicUrl(data.path);

      uploaded.push({
        imageUrl: publicUrlData.publicUrl,
        position: i
      });
    }
    return uploaded;
  };

  const handleSubmit = async (e) => {
  e.preventDefault();
  setUploading(true);

  try {
    let uploadedImages = [];

    if (images.length > 0) {
      uploadedImages = await uploadImages();
    }

    const payload = {
      ...formData,
      price: Number(formData.price),
      administrationFee: Number(formData.administrationFee) || 0,
      bedrooms: isLot ? 0 : Number(formData.bedrooms) || 0,
      bathrooms: isLot ? 0 : Number(formData.bathrooms) || 0,
      parkingSpaces: isLot ? 0 : Number(formData.parkingSpaces) || 0,
      lotArea: Number(formData.lotArea) || 0,
      builtArea: Number(formData.builtArea) || 0,
      operationType:
        formData.operationType === "N/A" ? null : formData.operationType,
      images: uploadedImages.length > 0 ? uploadedImages : formData.images || []
    };

    if (propertyToEdit?.id) {
      await updateProperty(propertyToEdit.id, payload);
    } else {
      await createProperty(payload);
    }

    onClose();
  } catch (err) {
    console.error(err);
    alert("Error guardando propiedad");
  } finally {
    setUploading(false);
  }
};


  if (!isOpen) return null;

  return (
    <div style={{
      position:"fixed", top:0, left:0, width:"100%", height:"100%",
      backgroundColor:"rgba(0,0,0,0.7)", display:"flex",
      justifyContent:"center", alignItems:"center", zIndex:1000
    }} onClick={onClose}>
      <div style={{
        backgroundColor:"#2b2b2b",
        borderRadius:10,
        width:"90%",
        maxWidth:800,
        maxHeight:"90%",
        overflowY:"auto",
        padding:20,
        color:"#fff",
        position:"relative"
      }} onClick={e=>e.stopPropagation()}>

        <h2>
        {propertyToEdit ? "Editar Propiedad" : "Agregar Propiedad"}
        </h2>

        <form onSubmit={handleSubmit}
              style={{ display:"flex", flexDirection:"column", gap:10 }}>

          <input type="text" name="title"
                 placeholder="Título"
                 value={formData.title}
                 onChange={handleChange}
                 required />

          <input type="number" name="price"
                 placeholder="Precio"
                 value={formData.price || ""}
                 onChange={handleChange}
                 required/>

          <input type="number" name="administrationFee"
                 placeholder="Administración"
                 value={formData.administrationFee || ""}
                 onChange={handleChange}/>



          <select name="propertyType"
                  value={formData.propertyType}
                  onChange={handleChange}>
            {PROPERTY_TYPES.map(t =>
              <option key={t} value={t}>
                {t.replaceAll("_"," ")}
              </option>
            )}
          </select>

          <select name="operationType"
                  value={formData.operationType}
                  onChange={handleChange}>
            {OPERATION_TYPES.map(op =>
              <option key={op} value={op}>
                {op.replaceAll("_"," ")}
              </option>
            )}
          </select>

          <input type="text" name="address"
                 placeholder="Dirección"
                 value={formData.address}
                 onChange={handleChange} />

          <input type="text" name="city"
                 placeholder="Ciudad"
                 value={formData.city}
                 onChange={handleChange} />

          <input type="text" name="department"
                 placeholder="Departamento"
                 value={formData.department}
                 onChange={handleChange} />

          <input type="text" name="neighborhood"
                 placeholder="Barrio"
                 value={formData.neighborhood}
                 onChange={handleChange} />

          <textarea name="propertyDescription"
                    placeholder="Descripción de la propiedad"
                    value={formData.propertyDescription}
                    onChange={handleChange} />

          <textarea name="locationDescription"
                    placeholder="Descripción de la ubicación"
                    value={formData.locationDescription}
                    onChange={handleChange} />

          {/* 👇 SOLO SI NO ES LOTE */}
          <div style={{display:"flex", gap:10}}>
            <input type="number" name="bedrooms"
                  placeholder="Habitaciones"
                  value={formData.bedrooms}
                  onChange={handleChange} />

            <input type="number" name="bathrooms"
                  placeholder="Baños"
                  value={formData.bathrooms}
                  onChange={handleChange} />

            <input type="number" name="parkingSpaces"
                  placeholder="Parqueaderos"
                  value={formData.parkingSpaces}
                  onChange={handleChange} />
          </div>


          <div style={{display:"flex", gap:10}}>
            <input type="number" name="lotArea"
                   placeholder="Área lote (m²)"
                   value={formData.lotArea}
                   onChange={handleChange} />

            <input type="number" name="builtArea"
                   placeholder="Área construida (m²)"
                   value={formData.builtArea}
                   onChange={handleChange} />
          </div>

          <input type="file" multiple onChange={handleImagesChange} />

          {imagePreviews.length>0 && (
            <div style={{
              display:"flex",
              gap:5,
              overflowX:"auto",
              marginTop:10
            }}>
              {imagePreviews.map((url,i)=>
                <img key={i}
                     src={url}
                     alt={`preview ${i}`}
                     style={{
                       width:100,
                       height:70,
                       objectFit:"cover",
                       borderRadius:4
                     }}
                />
              )}
            </div>
          )}

          <button type="submit"
                  disabled={uploading}
                  style={{
                    marginTop:15,
                    padding:"10px 15px",
                    backgroundColor:"#3498db",
                    color:"#fff",
                    border:"none",
                    borderRadius:6,
                    cursor:"pointer",
                    fontWeight:"bold"
                  }}>
            {uploading ? "Guardando..." : propertyToEdit ? "Actualizar Propiedad" : "Agregar Propiedad"}

          </button>

        </form>

        <button onClick={onClose}
                style={{
                  position:"absolute",
                  top:15,
                  right:15,
                  backgroundColor:"red",
                  color:"#fff",
                  border:"none",
                  borderRadius:"50%",
                  width:30,
                  height:30,
                  fontWeight:"bold",
                  cursor:"pointer"
                }}>
          ✕
        </button>
      </div>
    </div>
  );
}
