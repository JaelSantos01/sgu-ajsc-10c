const ApiController = {};
const ENV = import.meta.env;
const API_URL = `http://${ENV.VITE_API_HOST}:${ENV.VITE_API_PORT}${ENV.VITE_API_BASE}/users`;

// Obtener todos los usuarios
ApiController.getAllUsers = async () => {
  return await fetch(`${API_URL}`, {
    method: 'GET',
    headers: { 'Accept': 'application/json',
      'Content-Type': 'application/json'
     }
  }).then(res => res.json()).catch(console.log);
};

// Crear nuevo usuario
ApiController.createUser = async (user) => {
  return await fetch(`${API_URL}/create`, {
    method: 'POST',
    headers: { 
      'Accept': 'application/json',
      'Content-Type': 'application/json' },
    body: JSON.stringify(user)
  }).then(res => res.json()).catch(console.log);
};

// Actualizar usuario
ApiController.updateUser = async (id, user) => {
  return await fetch(`${API_URL}/${id}`, {
    method: 'PUT',
    headers: {
      'Accept': 'application/json', 'Content-Type': 'application/json' },
    body: JSON.stringify(user)
  }).then(res => res.json()).catch(console.log);
};

// Eliminar usuario
ApiController.deleteUser = async (id) => {
  return await fetch(`${API_URL}/${id}`, {
    method: 'DELETE',
    headers: { 
      'Accept': 'application/json', 'Content-Type': 'application/json' }
  }).then(res => res.json()).catch(console.log);
};

ApiController.getUserByName = async (name) => {
  const res = await fetch(`${API_URL}/${name}`);
  return res.json();
};


export default ApiController;
