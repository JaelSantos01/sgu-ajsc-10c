package utez.edu.com.server.modules.users.controller;

import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utez.edu.com.server.modules.users.model.User;
import utez.edu.com.server.modules.users.model.UserRepository;
import utez.edu.com.server.utils.TypesResponse;
import utez.edu.com.server.utils.Message;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;

@Transactional
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger((UserService.class));
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Get All
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll(){
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(new Message(users, "Listado completo de usuarios", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Get {name}
    @Transactional(readOnly = true)
    public ResponseEntity<Message> finByName (String name){
        Optional<User> useropt = userRepository.findByName(name);
        if(!useropt.isPresent()){
            return new ResponseEntity<>(new Message("Persona no encontrada", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(useropt.get(), "Persona encontrada", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Create
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<Message> create(User user) {
        if (user.getName() == null || user.getLastname() == null ||
            user.getEmail() == null || user.getPhone() == null) {
            return new ResponseEntity<>(new Message("Faltan campos obligatorios", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        if (user.getName().length() > 30 || user.getLastname().length() > 30) {
            return new ResponseEntity<>(new Message("Nombre o apellido demasiado largo", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(new Message("Ya existe un usuario con ese mismo correo", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByPhone(user.getPhone())) {
            return new ResponseEntity<>(new Message("Ya existe un usuario con ese teléfono", TypesResponse.WARNING), HttpStatus.CONFLICT);
        }

        user = userRepository.saveAndFlush(user);
        return new ResponseEntity<>(new Message(user, "Usuario registrado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    // UPDATE
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<Message> update (User user){
        Optional<User> useropt = userRepository.findById(user.getId());
        if(!useropt.isPresent()){
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        Optional<User> existingByPone = userRepository.findByPhone(user.getPhone());
        if (existingByPone.isPresent() && !existingByPone.get().getId().equals(user.getId())) {
            return new ResponseEntity<>(new Message("Ya existe un usuario con ese teléfono", TypesResponse.WARNING), HttpStatus.CONFLICT);
        }

        User existing = useropt.get();
        existing.setName(user.getName());
        existing.setLastname(user.getLastname());
        existing.setEmail(user.getEmail());
        existing.setPhone(user.getPhone());

        userRepository.saveAndFlush((existing));
        return  new ResponseEntity<>(new Message(existing,"Usuairo actualizado", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    //DELETE
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<Message> delete (Long id){
        Optional<User> useropt = userRepository.findById(id);
        if(!useropt.isPresent()){
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Usuario eliminado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
