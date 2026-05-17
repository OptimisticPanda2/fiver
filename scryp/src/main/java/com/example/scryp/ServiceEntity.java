package com.example.scryp;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class ServiceEntity {
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @Id
    @GeneratedValue
    private int id;

    private String title;
    private String description;
    private int price;
    private String category;
    private int deliveryDays;
    private String techStack;
    private String githubLink;
    private String demoVideoLink;
    private String portfolioLink;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(int deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getDemoVideoLink() {
        return demoVideoLink;
    }

    public void setDemoVideoLink(String demoVideoLink) {
        this.demoVideoLink = demoVideoLink;
    }

    public String getPortfolioLink() {
        return portfolioLink;
    }

    public void setPortfolioLink(String portfolioLink) {
        this.portfolioLink = portfolioLink;
    }
}