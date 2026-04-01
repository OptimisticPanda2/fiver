package com.example.fiver;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;
@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }
    @PostMapping("/users")
    public ResponseEntity<User>  addUser(@RequestBody User user)
    {   User savedUser = userService.addUser(user);
        return ResponseEntity.status(201).body(savedUser);
    }
    @GetMapping("/user")
    public ResponseEntity<List<User>>getAllUser() {
        List<User> user = userService.getAllUser();
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable int id)
    {   User user = userService.findUserById(id);
        if(user!=null)
        {
            return ResponseEntity.ok(user);}
        else
        {return ResponseEntity.notFound().build();}
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id , @RequestBody User user)
    {   User newUser = userService.findUserById(id);
        if(newUser!=null)
        { User updatedUser = userService.updateUser(id,user);
            return ResponseEntity.ok(updatedUser);
        }
        else{return ResponseEntity.notFound().build();}
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id )
    {   User user = userService.findUserById(id);
        if(user!=null)
        {
         userService.deleteUser(id);
         return ResponseEntity.noContent().build();
        }
        else{return ResponseEntity.notFound().build();}
    }
    @PatchMapping ("/user/{id}")
    public ResponseEntity<User> updatePartialUser(@PathVariable int id, @RequestBody User user ) {
        User suser = userService.findUserById(id);
        if (suser != null) {
            User updatedUser = userService.updatePartialUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}