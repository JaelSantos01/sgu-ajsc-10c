import Swal from "sweetalert2";

export const showSuccess = (title, text) => {
  Swal.fire({
    icon: "success",
    title,
    text,
    confirmButtonColor: "#3085d6",
  });
};

export const showError = (title, text) => {
  Swal.fire({
    icon: "error",
    title,
    text,
    confirmButtonColor: "#d33",
  });
};

export const showConfirmDelete = () => {
  return Swal.fire({
    title: "¿Estás seguro?",
    text: "Esta acción eliminará el registro permanentemente",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#d33",
    cancelButtonColor: "#6c757d",
    confirmButtonText: "Sí, eliminar",
    cancelButtonText: "Cancelar",
  });
};

export const showConfirmSave = () => {
  return Swal.fire({
    title: "¿Deseas guardar este usuario?",
    icon: "question",
    showCancelButton: true,
    confirmButtonText: "Sí, guardar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#3085d6",
  });
};

export const showConfirmUpdate = () => {
  return Swal.fire({
    title: "¿Deseas actualizar este usuario?",
    icon: "question",
    showCancelButton: true,
    confirmButtonText: "Sí, actualizar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#3085d6",
  });
};
