package com.example.scryp;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ServiceDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500)
    private String description;

    @Min(value = 1, message = "Price must be greater than 0")
    private int price;

    @NotBlank(message = "Category is required")
    private String category;

    @Min(value = 1, message = "Delivery days must be at least 1")
    private int deliveryDays;

    @NotBlank(message = "Tech stack is required")
    private String techStack;

    @NotBlank(message = "Github link is required")
    private String githubLink;

    @NotBlank(message = "Demo video link is required")
    private String demoVideoLink;

    @NotBlank(message = "Portfolio link is required")
    private String portfolioLink;

    // GETTERS AND SETTERS


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