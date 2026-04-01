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
    public String addUser(@RequestBody User user)
    {
        return userService.addUser(user);
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
    public String updateUser(@PathVariable int id , @RequestBody User user)

    {
        return userService.updateUser(id,user);
    }
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable int id )
    {
        return userService.deleteUser(id);
    }
    @PatchMapping ("/user/{id}")
    public String updatePartialUser(@RequestBody User user , @PathVariable int id )
    {
       return  userService.updatePartialUser(user,id);
    }

}