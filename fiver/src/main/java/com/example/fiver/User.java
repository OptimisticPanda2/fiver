package com.example.fiver;
import jakarta.persistence.*;
import java.util.List;
@Entity
public class User {
    @Id
    @GeneratedValue
    public int id;
    public String name ;
    public String email;
    public String password;
    public String role;
    @OneToMany(mappedBy = "user")
    List<ServiceEntity> services;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
