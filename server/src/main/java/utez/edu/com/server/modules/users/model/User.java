package utez.edu.com.server.modules.users.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    //CRUD de usuarios donde gestiones su
    //información personal (nombre completo, correo electrónico y número de teléfono)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "Varchar(30)")
    private String name; 

    @Column(name = "lastname", columnDefinition = "Varchar(30)")
    private String lastname;

    @Column(name = "email", columnDefinition= "Varchar(50)" )
    private String email;

    @Column(name = "phone", columnDefinition = "Varchar(10)")
    private String phone;

    public User(){}

    public User(String name, String lastname, String email, String phone) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public User(Long id, String name, String lastname, String email, String phone){
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}