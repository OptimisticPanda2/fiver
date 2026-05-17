package com.example.scryp;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
public class CreateUserDTO {
    @NotBlank(message="Name must not be blank")
    private String name;
    @Email(message = "Please Enter a valid Email")
    private String email;
    @Size(min = 6 , max = 15 , message = "Password Can Contain Maximum 15 characters")
    private String password;
    @NotBlank(message = "Please Enter Your Role")
    private String role;
    @NotBlank(message = "Please Enter your speciality")


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

}
