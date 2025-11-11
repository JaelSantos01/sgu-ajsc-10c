package utez.edu.com.server.modules.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.com.server.modules.users.model.User;
import utez.edu.com.server.utils.Message;

@RestController
@RequestMapping("/sgu-api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    //Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<Message> getAllUsers() {
        return  userService.findAll();
    }

    //Obtener un usuario por nombre
    @GetMapping("/{name}")
    public ResponseEntity<Message> getUserByName(@PathVariable String name){
        return userService.finByName(name);
    }

    //crear un nuevo usuario
    @PostMapping("/create")
    public ResponseEntity<Message> createUser(@RequestBody User user){
        return  userService.create(user);
    }

    //Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId((id));
        return userService.update(user);
    }

    //Eliminar un usuario por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id){
        return userService.delete(id);
    }
}
