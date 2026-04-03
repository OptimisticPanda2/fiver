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
import jakarta.validation.Valid;
import java.util.List;
import java.util.ArrayList;
@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }
    @PostMapping("/users")
    public ResponseEntity<UserDTO>  addUser(@Valid @RequestBody CreateUserDTO cuDTO)
    {   UserDTO saveDTO = userService.addUser(cuDTO);
        return ResponseEntity.status(201).body(saveDTO);
    }
    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>>getAllUser() {
        List<User> allUser = userService.getAllUser();
        List<UserDTO> userDTO = new ArrayList<>();
        for(User user : allUser)
        {
            userDTO.add(userService.convertToDTO(user));
        }
            return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable int id)
    {   User user = userService.findUserById(id);
        UserDTO userDTO = userService.convertToDTO(user);
        if(userDTO!=null)
        {
            return ResponseEntity.ok(userDTO);}
        else
        {return ResponseEntity.notFound().build();}
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id , @RequestBody User user)
    {   User newUser = userService.findUserById(id);
         User newData = userService.updateUser(id,newUser);
        return ResponseEntity.ok(newData);
    }
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable int id )
    {  userService.deleteUser(id);
        return  "User Deleted Successfully";
    }
    @PatchMapping ("/user/{id}")
    public ResponseEntity<User> updatePartialUser(@PathVariable int id, @RequestBody User user ) {
       User checkUser = userService.findUserById(id);
       if(user!=null)
       {User updatedUser = userService.updatePartialUser(id, user);
        return ResponseEntity.ok(updatedUser);}
       else{return ResponseEntity.notFound().build();}
    }
}