import { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import ApiController from "./models/controller/api.controller";
import ModalAddUser from "./utils/ModalAddUser";
import ModalEditUser from "./utils/ModalEditUser";
import { showConfirmDelete, showSuccess } from "./utils/alerts";

function App() {
  const [users, setUsers] = useState([]);
  const [showAdd, setShowAdd] = useState(false);
  const [showEdit, setShowEdit] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");

  const loadUsers = async () => {
    const data = await ApiController.getAllUsers();
    setUsers(data.data || []);
  };

  useEffect(() => {
    loadUsers();
  }, []);

  const handleSaveUser = async (user) => {
    await ApiController.createUser(user);
    showSuccess("Usuario creado correctamente");
    loadUsers();
  };

  const handleUpdateUser = async (user) => {
    await ApiController.updateUser(user.id, user);
    showSuccess("Usuario actualizado correctamente");
    loadUsers();
  };

  const handleDelete = async (id) => {
    const confirmed = await showConfirmDelete();
    if (confirmed) {
      await ApiController.deleteUser(id);
      showSuccess("Usuario eliminado correctamente");
      loadUsers();
    }
  };

    const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchTerm.trim()) {
      loadUsers(); // si está vacío, recarga todo
      return;
    }
    try {
      const data = await ApiController.getUserByName(searchTerm.trim());
      if (data.data) {
        setUsers([data.data]); // muestra solo el encontrado
      } else {
        showError("Usuario no encontrado");
      }
    } catch (err) {
      showError("Error al buscar usuario");
    }
  };

  return (
    <>
      <nav className="navbar bg-body-tertiary">
        <div className="container-fluid">
          <a className="navbar-brand fw-bold">SGU-AJSC-10C</a>
            <form className="d-flex" role="search" onSubmit={handleSearch}>
            <input
              className="form-control me-2"
              type="search"
              placeholder="Buscar por nombre"
              aria-label="Buscar"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          <button className="btn btn-outline-success me-2" type="submit">
              Buscar
            </button>
          </form>
          <div className="d-flex">
            <button className="btn btn-outline-primary" onClick={() => setShowAdd(true)}>
              <i className="bi bi-plus-lg"></i>
            </button>
          </div>
        </div>
      </nav>

      <div className="d-flex justify-content-center m-4">
        <table className="table table-striped table-hover w-auto text-center shadow-sm">
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Apellidos</th>
              <th>Email</th>
              <th>Teléfono</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {users.map((u) => (
              <tr key={u.id}>
                <td>{u.name}</td>
                <td>{u.lastname}</td>
                <td>{u.email}</td>
                <td>{u.phone}</td>
                <td>
                  <button
                    className="btn btn-sm btn-outline-primary me-2"
                    onClick={() => { setSelectedUser(u); setShowEdit(true); }}
                  >
                    <i className="bi bi-pencil-square"></i>
                  </button>
                  <button
                    className="btn btn-sm btn-outline-danger"
                    onClick={() => handleDelete(u.id)}
                  >
                    <i className="bi bi-trash"></i>
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <ModalAddUser show={showAdd} handleClose={() => setShowAdd(false)} onSave={handleSaveUser} />
      <ModalEditUser show={showEdit} handleClose={() => setShowEdit(false)} user={selectedUser} onUpdate={handleUpdateUser} />
    </>
  );
}

export default App;
