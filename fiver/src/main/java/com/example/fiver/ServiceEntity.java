package com.example.fiver;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="service")
public class ServiceEntity {
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @Id
    @GeneratedValue
    int id;
    String title;
    String description;
    int price;

    public ServiceEntity() {}
    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




}