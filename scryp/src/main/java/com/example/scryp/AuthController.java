package com.example.scryp;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService)
    {
        this.userService = userService;
    }
    // request to login user
    @PostMapping("/auth/login")
    public String login(@RequestBody LoginRequest request)
    {
        return userService.login(request);
    }
    // request to register user
    @PostMapping("/auth/register")
    public String register( @Valid @RequestBody CreateUserDTO cuDTO)
    {
        return userService.register(cuDTO);
    }
}
