package com.example.fiver;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
@CrossOrigin
@RestController

public class UserController {
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }
    @PostMapping("/users")
    public ApiResponse addUser(@Valid @RequestBody CreateUserDTO cuDTO)
    {   return userService.addUser(cuDTO);

    }
    @PostMapping(value = "/service/{id}", consumes = "multipart/form-data")
    public ServiceEntity addService(
        @PathVariable int id,
        @RequestParam("file") MultipartFile file,
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("price") int price
        ) throws IOException {
        ServiceEntity service = new ServiceEntity();
        service.setTitle(title);
        service.setDescription(description);
        service.setPrice(price);

        return userService.createService(id, service, file);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request)
    {
        return userService.login(request);
    }
    @GetMapping("/user")
    public Page<UserResponseDTO> getAllUser( Pageable pageable) {
        Page<UserResponseDTO> allUser = userService.getAllUser(pageable);
        return allUser;
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable int id)
    {
        UserResponseDTO userDTO = userService.getUserWithService(id);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/services")
    public PaginatedResponse getServices(Pageable pageable)
    {
        return userService.getService(pageable);
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
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file)throws IOException
    {
        String folderPath = "E:\\uploads\\";
        File folder = new File(folderPath);
        if(!folder.exists())
        {
            folder.mkdir();
        }
        String filePath = folderPath + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return "Saved at : " +filePath;
    }
}