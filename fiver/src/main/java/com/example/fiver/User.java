package com.example.fiver;
import jakarta.persistence.*;
@Entity
public class User {
    @Id
    @GeneratedValue
    public int id;

    public String name ;
    public String email;
    public String password;
    public String role;
}
