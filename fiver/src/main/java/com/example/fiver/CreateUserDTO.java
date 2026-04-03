package com.example.fiver;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
public class CreateUserDTO {
    @NotNull(message="Name must not be blank")
    private String name;
    @Email(message = "Please Enter a valid Email")
    private String email;
    @Size(max = 12 , message = "Password Can Contain Maximum 12 characted")
    private String password;
    @NotBlank(message = "Please Enter Your Role")
    private String role;
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
