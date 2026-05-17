package com.example.scryp;
import jakarta.persistence.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.List;
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue
    public int id;
    public String name ;
    public String email;
    public String password;
    public String role;
    @OneToMany(mappedBy = "user")
    List<ServiceEntity> services;
    @OneToMany(mappedBy = "client")
    private List<ProjectRequest> sentRequests;
    @OneToMany(mappedBy = "freelancer")
    private List<ProjectRequest> recievedRequests;

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

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }

    public List<ProjectRequest> getSentRequests() {
        return sentRequests;
    }

    public void setSentRequests(List<ProjectRequest> sentRequests) {
        this.sentRequests = sentRequests;
    }

    public List<ProjectRequest> getRecievedRequests() {
        return recievedRequests;
    }

    public void setRecievedRequests(List<ProjectRequest> recievedRequests) {
        this.recievedRequests = recievedRequests;
    }

    @Override
    public String getUsername() {
        return this.email; // email = username
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + this.role.toUpperCase());
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
