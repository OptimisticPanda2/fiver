package com.example.scryp;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectRequestDTO {

    private int serviceId;
    @NotBlank(message = "Project title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 to 100 characters")
    private String projectTitle;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 to 500 characters")
    private String description;

    @Min(value = 100, message = "Budget must be greater than 100")
    private double budget;

    @NotBlank(message = "Status is required")
    private String status;

    // GETTERS AND SETTERS


    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}