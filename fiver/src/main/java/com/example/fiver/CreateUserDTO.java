package com.example.fiver;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
public class CreateUserDTO {
    @NotBlank(message="Name must not be blank")
    private String name;
    @Email(message = "Please Enter a valid Email")
    private String email;
    @Size(min = 6 , max = 12 , message = "Password Can Contain Maximum 12 characted")
    private String password;
    @NotBlank(message = "Please Enter Your Role")
    private String role;
    @NotBlank(message = "Please Enter your speciality")
    private String title;
    @NotBlank(message="Please Enter how will you help you client")
    private String description;
   @NotNull(message = "Please Enter the amount you'll charge your client to help them")
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}
